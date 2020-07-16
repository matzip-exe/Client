package com.example.matzip_exe.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentDetail
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.model.ModelDetailList
import com.example.matzip_exe.utils.DataSynchronized
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    private lateinit var webView: WebView
    private var avgCost: Int = 0

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
        avgCost = intent.getIntExtra("avgCost", 0)


        init()
    }

    private fun init() {
        setDataListener()
        getBizDetail()
        fragmentDetail(name, locatex, locatey)
        initChart()
//        setGraph()
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
        val tvRoadAddress = findViewById<TextView>(R.id.detail_roadAddress)
        val tvAddress = findViewById<TextView>(R.id.detail_address)
        val tvTelNum = findViewById<TextView>(R.id.detail_telNum)
        val tvAvgCost = findViewById<TextView>(R.id.detail_avgCost)

        tvVisitcount.text = visitcount+"회"
        tvName.text = name
        tvType.text = type
        tvRoadAddress.text = item.roadAddress
        tvAddress.text = "지번: "+item.address
        tvTelNum.text = item.telNum
        tvAvgCost.text = "1인당 평균 "+avgCost.toString()+"원"
    }

//    @SuppressLint("SetJavaScriptEnabled")
//    private fun setGraph() {
//        var htmlContent: String = ""
//        webView = findViewById(R.id.detail_webView)
//        val webSettings: WebSettings = webView.settings
//        webSettings.javaScriptEnabled = true
//        webView.settings.javaScriptCanOpenWindowsAutomatically = true
//
//        try{
//            val inputStream = this.assets.open("html/graph.html")
//            htmlContent = inputStream.bufferedReader().use {
//                it.readText()
//            }
//        }
//        catch (e: Exception){
//            e.printStackTrace()
//        }
//
//        webView.loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "utf-8", null)
//    }

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
            axisLeft.axisMaximum = 20f
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
                set = createSet()
                data.addDataSet(set)

            for (i in item.monthlyVisits.indices) {
                data.addEntry(Entry(set.entryCount.toFloat(), item.monthlyVisits[i].count.toFloat()), 0)
            }
            data.notifyDataChanged()
            detail_chart.apply {
                notifyDataSetChanged()
                setVisibleXRangeMaximum(12f)
                setPinchZoom(false)
                isDoubleTapToZoomEnabled = false
                description.text = ""
                description.textSize = 14f
                setExtraOffsets(2f, 2f, 2f, 2f)
            }
        }
        val vf: ValueFormatter =
            object : ValueFormatter() {
                //value format here, here is the overridden method
                override fun getFormattedValue(value: Float): String {
                    return "" + value.toInt()
                }
            }
        data.setValueFormatter(vf)
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "count") //label: count
        set.apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = resources.getColor(R.color.colorMain)
            setCircleColor(resources.getColor(R.color.colorMain))
            valueTextSize = 12f
            lineWidth = 3f
            circleRadius = 2f
            fillAlpha = 0
            fillColor = resources.getColor(R.color.colorMain)
            highLightColor = Color.BLACK
            setDrawValues(true)
        }
        return set
    }


}