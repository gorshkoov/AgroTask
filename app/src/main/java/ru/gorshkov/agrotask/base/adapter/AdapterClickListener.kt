package ru.gorshkov.agrotask.base.adapter


interface AdapterClickListener<T> {
    fun onClick(item : T)
}