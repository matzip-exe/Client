package com.example.matzip_exe.activities

import android.graphics.Color
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentDetail
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.model.ModelCheckRegion
import com.example.matzip_exe.model.ModelDetailList
import com.example.matzip_exe.model.ModelMatZipList
import com.example.matzip_exe.utils.DataSynchronized
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PolygonOverlay
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_matzip_list.*
import org.json.JSONObject.NULL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class Detail: AppCompatActivity(), GetDataListener {
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

    private val AdminData = DataSynchronized()

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
        setDataListener()
        getBizDetail()
        fragmentDetail(name, locatex, locatey)
        initChart()
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

    private fun setDataListener() {
        AdminData.setOnGetDataListener(this)
    }

    private fun getBizDetail() {
        AdminData.getBizDetail(region, name)
    }

    override fun getData(data: Any?) {
        modelBizDetail = data as ModelBizDetail
        item = ModelDetailList(modelBizDetail.items.telNum, modelBizDetail.items.address, modelBizDetail.items.roadAddress, modelBizDetail.items.monthlyVisits)
        initTempTexts()
    }

    private fun initTempTexts() {
        val tvVisitcount = findViewById<TextView>(R.id.detail_visitcount)
        val tvName = findViewById<TextView>(R.id.detail_name)
        val tvType = findViewById<TextView>(R.id.detail_type)
//        val tvRoadAddress = findViewById<TextView>(R.id.detail_roadAddress)
        val tvList = findViewById<TextView>(R.id.detail_countList)

        tvVisitcount.text = visitcount+"회"
        tvName.text = name
        tvType.text = type
//        tvRoadAddress.text = item.roadAddress
        tvList.text ="${item.monthlyVisits[0].date}, ${item.monthlyVisits[0].count}"
    }

    private fun fragmentDetail(name: String, locatex: Double, locatey: Double){
        supportFragmentManager.beginTransaction().add(R.id.detail_map_layout, FragmentDetail(name, locatex, locatey)).commit()
    }

    private fun initChart() {
        val xAxis: XAxis = detail_chart.xAxis

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 10f
            setDrawGridLines(false)
            granularity = 1f
            axisMinimum = 0f
            isGranularityEnabled = false
        }
        detail_chart.apply {
            axisRight.isEnabled = false
            axisLeft.axisMaximum = 60f
            legend.apply {
                textSize = 14f
                verticalAlignment = Legend.LegendVerticalAlignment.TOP
                horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
        }
        val lineData = LineData()
        detail_chart.data = lineData
        fillChart()
    }

    private fun fillChart() {
        val data: LineData = detail_chart.data

        data.let {
            var set: ILineDataSet? = data.getDataSetByIndex(0)
//            if (set == null) {
                set = createSet()
                data.addDataSet(set)
//            }
            data.addEntry(Entry(set.entryCount.toFloat(), 22f), 0)
            data.addEntry(Entry(set.entryCount.toFloat(), 47f), 0)
            data.addEntry(Entry(set.entryCount.toFloat(), 31f), 0)
            data.addEntry(Entry(set.entryCount.toFloat(), 50f), 0)
            data.notifyDataChanged()
            detail_chart.apply {
                notifyDataSetChanged()
//                moveViewToX(data.entryCount.toFloat())
                setVisibleXRangeMaximum(12f)
                setPinchZoom(false)
                isDoubleTapToZoomEnabled = false
                description.text = ""
//                setBackgroundColor(resources.getColor(R.color.material_on_background_disabled))
                description.textSize = 14f
                setExtraOffsets(2f, 2f, 2f, 2f)
            }
        }
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "count") //label: count
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.colorMain)
            setCircleColor(resources.getColor(R.color.colorMain))
            valueTextSize = 12f
            lineWidth = 3f
            circleRadius = 0f
            fillAlpha = 0
            fillColor = resources.getColor(R.color.colorMain)
            highLightColor = Color.BLACK
            setDrawValues(true)
        }
        return set
    }
}