package ru.gorshkov.agrotask.features.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gorshkov.agrotask.data.pojo.AgroField
import ru.gorshkov.agrotask.features.map.interactors.FieldsInteractor

class MapViewModel(private val fieldsInteractor: FieldsInteractor) : ViewModel(){
    val fieldsUpdateLiveData = MutableLiveData<List<AgroField>>()
    val filterLiveData = MutableLiveData<List<String>>()

    fun start() {
        fieldsUpdateLiveData.postValue(fieldsInteractor.getFields())
    }

    fun onSearchChanged(text: String) {
        fieldsUpdateLiveData.postValue(fieldsInteractor.searchItems(text))
    }

    fun onFilterClicked() {
        filterLiveData.postValue(fieldsInteractor.getYears())
    }

    fun onFilterSelected(year: String?) {
        fieldsUpdateLiveData.postValue(fieldsInteractor.filterItems(year))
    }
}