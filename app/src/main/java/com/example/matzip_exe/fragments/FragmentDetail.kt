package com.example.matzip_exe.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.UiThread
import androidx.fragment.app.FragmentActivity
import com.example.matzip_exe.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons

class FragmentDetail(private var name: String, private var locatex: Double,
                     private var locatey: Double
): FragmentActivity(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMap()
    }

    @UiThread
    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        marker = Marker(LatLng(locatey, locatex), MarkerIcons.BLACK)
        marker.captionText = name
        marker.captionTextSize = 16F
        marker.setCaptionAligns(Align.Top)
        marker.iconTintColor = Color.rgb(33, 83, 142)
        naverMap.cameraPosition = CameraPosition(LatLng(locatey, locatex), 16.0)
        marker.map = naverMap
    }

    private fun initMap() {
        val fm = supportFragmentManager
        val mapFragment = fm!!.findFragmentById(R.id.detail_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.detail_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }
}