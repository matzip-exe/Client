package com.example.matzip_exe.activities

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentDetail
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.model.ModelDetailList
import com.example.matzip_exe.model.ModelMatZipList
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PolygonOverlay
import kotlinx.android.synthetic.main.activity_matzip_list.*
import org.json.JSONObject.NULL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class Detail: AppCompatActivity() {
    private lateinit var visitcount: String
    private lateinit var name: String
    private lateinit var type: String
    private lateinit var area: String
    private lateinit var region: String
    private var locatex: Double = 0.0
    private var locatey: Double = 0.0
    private lateinit var modelBizDetail: ModelBizDetail
    private val myRetrofit = MyRetrofit()
    private lateinit var item: ModelDetailList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        visitcount = intent.getStringExtra("visitcount")!!
        name = intent.getStringExtra("name")!!
        type = intent.getStringExtra("type")!!
        area = intent.getStringExtra("area")!!
        region = intent.getStringExtra("region")!!
        locatex = intent.getDoubleExtra("locatex", 0.0)
        locatey = intent.getDoubleExtra("locatey", 0.0)

        init()
    }

    private fun init() {
        requestToServer()
        fragmentDetail(name, locatex, locatey)
    }

    private fun requestToServer() {
        val bizDetail = myRetrofit.makeService().getBizDetail(region = region, bizName = name)
        bizDetail.enqueue(object: Callback<ModelBizDetail> {
            override fun onFailure(call: Call<ModelBizDetail>, t: Throwable) {
                Log.i("Detail Error", t.message!!)
            }

            override fun onResponse(call: Call<ModelBizDetail>, response: Response<ModelBizDetail>) {
                try {
                    modelBizDetail = response.body()!!
                    item = ModelDetailList(modelBizDetail.items.telNum, modelBizDetail.items.address, modelBizDetail.items.roadAddress, modelBizDetail.items.monthlyVisits)
                    initTempTexts()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    private fun initTempTexts() {
        val tvVisitcount = findViewById<TextView>(R.id.detail_visitcount)
        val tvName = findViewById<TextView>(R.id.detail_name)
        val tvType = findViewById<TextView>(R.id.detail_type)
        val tvRoadAddress = findViewById<TextView>(R.id.detail_roadAddress)

        tvVisitcount.text = visitcount
        tvName.text = name
        tvType.text = type
        tvRoadAddress.text = item.roadAddress
    }

    private fun fragmentDetail(name: String, locatex: Double, locatey: Double){
        supportFragmentManager.beginTransaction().add(R.id.detail_map_layout, FragmentDetail(name, locatex, locatey)).commit()
    }
}