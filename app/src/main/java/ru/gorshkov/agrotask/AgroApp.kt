package ru.gorshkov.agrotask

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.gorshkov.agrotask.di.appModule
import ru.gorshkov.agrotask.di.mapModule

class AgroApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AgroApp)
            modules(listOf(appModule, mapModule))
        }
    }
}