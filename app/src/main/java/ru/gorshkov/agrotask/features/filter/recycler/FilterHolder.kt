package ru.gorshkov.agrotask.features.filter.recycler

import android.view.View
import kotlinx.android.synthetic.main.holder_filter.view.*
import ru.gorshkov.agrotask.base.adapter.BaseViewHolder


class FilterHolder(private val view: View) : BaseViewHolder<String>(view) {
    override fun bind(item: String) {
        view.filter_item.text = item
    }
}