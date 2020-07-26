package com.example.matzip_exe.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matzip_exe.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.style.light.Position
import com.naver.maps.map.util.MarkerIcons

class FragmentDetail(name: String, locatex: Double, locatey: Double): Fragment(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker
    private var name = name
    private var locatex = locatex
    private var locatey = locatey

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()
    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0
        marker = Marker(LatLng(locatey, locatex), MarkerIcons.BLACK)
        marker.captionText = name
        marker.captionTextSize = 16F
        marker.setCaptionAligns(Align.Top)
        marker.iconTintColor = Color.rgb(249, 223, 111)

        naverMap.cameraPosition = CameraPosition(LatLng(locatey, locatex), 16.0)
        marker.map = naverMap
        println("onMapReadyFin")
    }

    private fun initMap() {
        val fm = fragmentManager
        val mapFragment = fm!!.findFragmentById(R.id.detail_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.detail_map, it).commit()
            }
        mapFragment.getMapAsync(this)
        println("initMap Fin")
    }
}