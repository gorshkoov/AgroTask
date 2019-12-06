package ru.gorshkov.agrotask.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gorshkov.agrotask.features.map.MapViewModel
import ru.gorshkov.agrotask.features.map.interactors.FieldsInteractor
import ru.gorshkov.agrotask.features.map.repositories.FieldsRepository
import ru.gorshkov.agrotask.features.map.repositories.LocalFieldsRepositoryImpl
import ru.gorshkov.agrotask.utils.DateUtils
import ru.gorshkov.agrotask.utils.MapUtils

val mapModule = module {
    viewModel { MapViewModel(get()) }
    single { FieldsInteractor(get()) }
    single { LocalFieldsRepositoryImpl(get()) as FieldsRepository }
}

val appModule = module {
    single { DateUtils() }
    single { MapUtils(androidContext()) }
}