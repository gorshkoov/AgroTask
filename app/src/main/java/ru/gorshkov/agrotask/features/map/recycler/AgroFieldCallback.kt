package ru.gorshkov.agrotask.features.map.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.gorshkov.agrotask.data.pojo.AgroField


class AgroFieldCallback(
    private val oldList: List<AgroField>,
    private val newList: List<AgroField>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition].id == newList[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }
}