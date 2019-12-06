package ru.gorshkov.agrotask.data.pojo

import androidx.annotation.ColorRes
import com.google.android.gms.maps.model.LatLng


data class AgroField(
    val id: Long = 0L,
    val size: Int = 0,
    val name: String = "",
    val description: String = "",
    val dateStart: Long = 0L,
    val dateEnd: Long = 0L,
    @ColorRes
    val color: Int = 0,
    var polygon: List<LatLng>
)