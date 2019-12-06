package ru.gorshkov.agrotask.features.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.github.ajalt.timberkt.Timber
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE
import com.google.android.gms.maps.MapsInitializer
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.EasyPermissions
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.base.adapter.AdapterClickListener
import ru.gorshkov.agrotask.base.fragment.BaseFragment
import ru.gorshkov.agrotask.data.pojo.AgroField
import ru.gorshkov.agrotask.features.filter.FilterDialog
import ru.gorshkov.agrotask.features.map.recycler.AgroFieldAdapter
import ru.gorshkov.agrotask.utils.DateUtils
import ru.gorshkov.agrotask.utils.MapUtils

class MapFragment : BaseFragment(), EasyPermissions.PermissionCallbacks {
    private var googleMap: GoogleMap? = null
    private var adapter: AgroFieldAdapter? = null

    private val mapViewModel: MapViewModel by viewModel()
    private val dateUtils: DateUtils by inject()
    private val mapUtils: MapUtils by inject()

    override val layoutRes = R.layout.fragment_map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapViewModel.fieldsUpdateLiveData.observe(this, Observer {
            googleMap?.clear()
            if (it.isNullOrEmpty()) {
                fields_recycler.visibility = View.INVISIBLE
                return@Observer
            }
            fields_recycler.visibility = View.VISIBLE
            (fields_recycler.adapter as AgroFieldAdapter).update(it)

            for (field in it) {
                googleMap?.addPolygon(mapUtils.getFieldPolygon(field))
            }
            mapUtils.moveCamera(googleMap, it[0].polygon, it[0].name)
        })

        mapViewModel.filterLiveData.observe(this, Observer {
            showFilterDialog(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        map_view.onCreate(savedInstanceState)
        MapsInitializer.initialize(activity)

        requestPermissions()
        initAdapter()
        initRecycler()

        search_edit_text.addTextChangedListener {
            mapViewModel.onSearchChanged(it.toString())
        }

        filter_button.setOnClickListener {
            mapViewModel.onFilterClicked()
        }
    }

    private fun getMap() {
        map_view.getMapAsync { googleMap ->
            initMap(googleMap)
            mapViewModel.start()
        }
    }

    private fun requestPermissions() {
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
            getMap()
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.message_denied),
                100, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun initRecycler() {
        with(fields_recycler) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

            adapter = this@MapFragment.adapter
        }
    }

    private fun initAdapter() {
        adapter = AgroFieldAdapter(dateUtils)
        adapter?.onClickListener = object : AdapterClickListener<AgroField> {
            override fun onClick(item: AgroField) {
                mapUtils.moveCamera(googleMap, item.polygon, item.name)

                adapter?.selectItem(item)
                val position = adapter?.getPosition(item)
                if (position != null) {
                    fields_recycler.scrollToPosition(position)
                }
            }
        }
    }

    private fun initMap(googleMap: GoogleMap) {
        this.googleMap = googleMap
        val isMyLocatioEnabled =
            EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        googleMap.uiSettings.isMyLocationButtonEnabled = isMyLocatioEnabled
        googleMap.isMyLocationEnabled = isMyLocatioEnabled
        googleMap.setOnPolygonClickListener {
            val item = adapter?.getFieldItem(it)
            adapter?.selectItem(item)
            mapUtils.moveCamera(googleMap, it.points, item?.name)

            val position = adapter?.getPosition(item)
            if (position != null) {
                fields_recycler.scrollToPosition(position)
            }
        }

        googleMap.setOnCameraMoveStartedListener {
            if (it == REASON_GESTURE) {
                search_edit_text.clearFocus()
                search_edit_text?.let { v ->
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }

        moveMyLocationButton()
    }

    private fun showFilterDialog(years: List<String>) {
        if (activity == null || activity?.supportFragmentManager == null) {
            Timber.e { "MapFragment::showFilterDialog: activity is null" }
            return
        }
        val filterDialog = FilterDialog()

        val args = Bundle()
        args.putStringArrayList(FilterDialog.YEARS_BUNDLE, ArrayList(years))

        filterDialog.arguments = args
        filterDialog.setTargetFragment(this, FilterDialog.REQUEST_CODE)
        filterDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        filterDialog.show(activity?.supportFragmentManager!!, filterDialog.javaClass.name)
    }

    //workaround. No other way how to move my location button
    private fun moveMyLocationButton() {
        val locationButton =
            (map_view.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(
                Integer.parseInt("2")
            )
        val padding = resources.getDimensionPixelSize(R.dimen.search_edit_text_height) +
                resources.getDimensionPixelOffset(R.dimen.padding_16dp)
        val rlp = locationButton.layoutParams as (RelativeLayout.LayoutParams)
        // position on right top
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
        rlp.setMargins(0, padding, 30, 0)
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilterDialog.REQUEST_CODE ->
                mapViewModel.onFilterSelected(data?.getStringExtra(FilterDialog.YEAR_EXTRA))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, R.string.message_denied, Toast.LENGTH_LONG).show()
        getMap()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(context, R.string.message_approved, Toast.LENGTH_LONG).show()
        getMap()
    }
}