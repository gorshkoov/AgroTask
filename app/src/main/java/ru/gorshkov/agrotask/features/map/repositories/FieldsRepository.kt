package ru.gorshkov.agrotask.features.map.repositories

import ru.gorshkov.agrotask.data.pojo.AgroField


interface FieldsRepository {
    fun getFields(): List<AgroField>

    fun onSearchChanged(text: String): List<AgroField>

    fun onFilterSelected(year: String?): List<AgroField>

    fun getYears() : List<String>
}