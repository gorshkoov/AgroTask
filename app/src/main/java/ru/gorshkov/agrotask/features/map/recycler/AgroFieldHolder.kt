package ru.gorshkov.agrotask.features.map.recycler

import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.holder_fields.view.*
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.base.adapter.BaseViewHolder
import ru.gorshkov.agrotask.data.pojo.AgroField
import ru.gorshkov.agrotask.utils.DateUtils


class AgroFieldHolder(
    private val view: View,
    private val dateUtils: DateUtils
) : BaseViewHolder<AgroField>(view) {

    fun setSelected(isSelected: Boolean) {
        val color: Int = if (isSelected)
            ContextCompat.getColor(itemView.context, R.color.gray)
        else
            ContextCompat.getColor(itemView.context, R.color.white)

        view.holder_root.setCardBackgroundColor(color)

    }

    override fun bind(item: AgroField) {
        view.field_id_text.text = view.resources.getString(R.string.field_name, item.id)
        view.field_description_text.text = item.description
        view.field_name_text.text = item.name
        view.field_size_text.text = view.resources.getString(R.string.field_size, item.size)
        view.date_start_text.text = dateUtils.getFieldDate(item.dateStart)
        view.date_end_text.text = dateUtils.getFieldDate(item.dateEnd)
    }
}