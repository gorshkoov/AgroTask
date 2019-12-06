package ru.gorshkov.agrotask.features.map.recycler

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.Polygon
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.base.adapter.BaseAdapter
import ru.gorshkov.agrotask.data.pojo.AgroField
import ru.gorshkov.agrotask.utils.DateUtils


class AgroFieldAdapter(private val dateUtils: DateUtils) :
    BaseAdapter<AgroField, AgroFieldHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun item(itemView: View) = AgroFieldHolder(itemView, dateUtils)

    override fun getItemViewType(position: Int) = R.layout.holder_fields

    fun update(newItems: List<AgroField>) {
        val diffResult = DiffUtil.calculateDiff(AgroFieldCallback(this.items, newItems))
        this.items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: AgroFieldHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.setSelected(position == selectedPosition)
    }

    fun getFieldItem(polygon: Polygon): AgroField? {
        val code = polygon.points[0]

        for (item in items) {
            if (code == item.polygon[0])
                return item
        }
        return null
    }

    fun getPosition(field: AgroField?) = items.indexOf(field)

    fun selectItem(field: AgroField?) {
        val position = items.indexOf(field)
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedPosition)
        }

        selectedPosition = position
        notifyItemChanged(selectedPosition)
    }
}