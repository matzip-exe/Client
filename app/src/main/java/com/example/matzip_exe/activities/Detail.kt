package com.example.matzip_exe.activities

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentDetail
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PolygonOverlay
import kotlinx.android.synthetic.main.activity_matzip_list.*


class Detail: AppCompatActivity() {

    private lateinit var visitcount: String
    private lateinit var name: String
    private lateinit var type: String
    private var locatex: Double = 0.0
    private var locatey: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        visitcount = intent.getStringExtra("visitcount")!!
        name = intent.getStringExtra("name")!!
        type = intent.getStringExtra("type")!!
        locatex = intent.getDoubleExtra("locatex", 0.0)!!
        locatey = intent.getDoubleExtra("locatey", 0.0)!!

        init()
    }

    private fun init() {
        initTempTexts()
        fragmentDetail(name, locatex, locatey)
    }

    private fun initTempTexts() {
        val tvVisitcount = findViewById<TextView>(R.id.detail_visitcount)
        val tvName = findViewById<TextView>(R.id.detail_name)
        val tvType = findViewById<TextView>(R.id.detail_type)

        tvVisitcount.text = visitcount
        tvName.text = name
        tvType.text = type
    }

    private fun fragmentDetail(name: String, locatex: Double, locatey: Double){
        supportFragmentManager.beginTransaction().add(R.id.detail_map_layout, FragmentDetail(name, locatex, locatey)).commit()
    }

}