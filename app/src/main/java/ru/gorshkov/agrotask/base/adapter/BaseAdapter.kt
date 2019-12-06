package ru.gorshkov.agrotask.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<Item, Holder : BaseViewHolder<Item>> constructor() : RecyclerView.Adapter<Holder>() {
    constructor(items: List<Item>) : this() {
        this.items = items
    }

    protected var items: List<Item> = listOf()
    var onClickListener: AdapterClickListener<Item>? = null

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, layoutId: Int
    ): Holder {
        val holder = item(
            LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
        )
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(it.tag as Item)
        }
        return holder
    }

    abstract fun item(itemView: View): Holder

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.tag = item
    }
}