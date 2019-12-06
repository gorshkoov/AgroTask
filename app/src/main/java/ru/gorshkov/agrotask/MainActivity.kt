package ru.gorshkov.agrotask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.gorshkov.agrotask.features.map.MapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopMapFragment()
    }

    private fun shopMapFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MapFragment())
            .commit()
    }
}
