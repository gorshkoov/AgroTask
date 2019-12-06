package ru.gorshkov.agrotask.features.map.interactors

import ru.gorshkov.agrotask.features.map.repositories.FieldsRepository


class FieldsInteractor(private val fieldsRepository: FieldsRepository) {
    fun getFields() = fieldsRepository.getFields()

    fun searchItems(text: String) = fieldsRepository.onSearchChanged(text)

    fun filterItems(year: String?) = fieldsRepository.onFilterSelected(year)

    fun getYears() = fieldsRepository.getYears()
}