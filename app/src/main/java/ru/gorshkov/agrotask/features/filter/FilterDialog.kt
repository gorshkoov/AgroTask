package ru.gorshkov.agrotask.features.filter

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_filter.*
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.base.adapter.AdapterClickListener
import ru.gorshkov.agrotask.base.fragment.BaseDialogFragment
import ru.gorshkov.agrotask.features.filter.recycler.FilterAdapter

class FilterDialog : BaseDialogFragment() {
    companion object {
        const val REQUEST_CODE = 111
        const val YEARS_BUNDLE = "years_bundle"
        const val YEAR_EXTRA = "year_extra"
    }

    private var adapter: FilterAdapter? = null
    private val allYears: String by lazy { resources.getString(R.string.all_years) }

    override val layoutRes = R.layout.dialog_filter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val years = arguments?.getStringArrayList(YEARS_BUNDLE)
        years?.add(allYears)

        adapter = FilterAdapter(years as List<String>)
        adapter?.onClickListener = object : AdapterClickListener<String> {
            override fun onClick(item: String) {
                val intent = Intent()
                if (TextUtils.equals(item, allYears)) {
                    intent.putExtra(YEAR_EXTRA, "")
                } else {
                    intent.putExtra(YEAR_EXTRA, item)
                }
                targetFragment?.onActivityResult(REQUEST_CODE, Activity.RESULT_OK, intent)
                dismiss()
            }
        }

        with(filter_recycler) {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@FilterDialog.adapter
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(R.string.choose_year)
        return dialog
    }
}