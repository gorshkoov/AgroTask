package ru.gorshkov.agrotask.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import ru.gorshkov.agrotask.R
import ru.gorshkov.agrotask.data.pojo.AgroField

class MapUtils(private val context : Context) {

    fun getFieldPolygon(field: AgroField): PolygonOptions {
        val color = ContextCompat.getColor(context, field.color)
        return PolygonOptions()
            .clickable(true)
            .addAll(field.polygon)
            .strokeColor(color)
            .fillColor(color)
    }

    fun moveCamera(map: GoogleMap?, coordinates: List<LatLng>, title: String?) {
        val center = getPolygonCenterPoint(coordinates)
        val cameraPosition = CameraPosition.Builder()
            .target(center)
            .zoom(9f)
            .build()
        val marker = map?.addMarker(getMarker(center, title))
        marker?.showInfoWindow()
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun getMarker(latLng: LatLng, title: String?) : MarkerOptions {
        return MarkerOptions()
            .position(LatLng(latLng.latitude, latLng.longitude))
            .title(title)
            .icon(bitmapDescriptorFromVector(R.drawable.ic_empty))
    }

    private fun getPolygonCenterPoint(polygonPointsList: List<LatLng>): LatLng {
        val centerLatLng: LatLng?
        val builder: LatLngBounds.Builder = LatLngBounds.Builder()
        for (element in polygonPointsList) {
            builder.include(element)
        }
        val bounds: LatLngBounds = builder.build()
        centerLatLng = bounds.center
        return centerLatLng
    }

    private fun bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}