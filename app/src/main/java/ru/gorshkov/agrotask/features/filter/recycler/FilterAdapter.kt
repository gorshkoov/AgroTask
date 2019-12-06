package ru.gorshkov.agrotask.features.filter.recycler

import android.view.View
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.base.adapter.BaseAdapter


class FilterAdapter(items : List<String>) : BaseAdapter<String, FilterHolder>(items) {

    override fun item(itemView: View) = FilterHolder(itemView)

    override fun getItemViewType(position: Int) = R.layout.holder_filter

}