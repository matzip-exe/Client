package com.example.matzip_exe.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matzip_exe.R
import com.example.matzip_exe.model.ModelPolygon
import com.example.matzip_exe.utils.ParsingPolygon
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PolygonOverlay

class FragmentMap: Fragment(), OnMapReadyCallback {
    private lateinit var naverMap: NaverMap
    private lateinit var polygonData: ModelPolygon

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMap()

    }

    override fun onMapReady(p0: NaverMap) {
        naverMap = p0

        naverMap.cameraPosition = CameraPosition(LatLng(37.5642135, 127.0016985), 9.0)

        setPolygonData()

    }

    private fun initMap(){
        val fm = fragmentManager
        val mapFragment = fm!!.findFragmentById(R.id.Map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.Map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun setPolygonData(){
        polygonData = ParsingPolygon(context!!).getJson()

        for (i in polygonData.items.indices){
            val temp = ArrayList<LatLng>()

            for (j in polygonData.items[i].Position.indices){
                temp.add(LatLng(polygonData.items[i].Position[j][1], polygonData.items[i].Position[j][0]))
            }

            makePolygon(polygonData.items[i].Division, temp.toList())
        }
    }

    private fun makePolygon(division: String, data: List<LatLng>){
        val polygon = PolygonOverlay()

        polygon.coords = data
        polygon.color = Color.alpha(0)
        polygon.outlineWidth = 5
        polygon.outlineColor = Color.RED

        polygon.map = naverMap

        polygon.setOnClickListener {
            Toast.makeText(context!!, division, Toast.LENGTH_SHORT).show()
            true
        }
    }
}