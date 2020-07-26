package com.example.matzip_exe.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
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
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.android.synthetic.main.activity_detail.*
import org.w3c.dom.Text
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
    private var modelBizDetail: ModelBizDetail? = null
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
    }

//    private fun requestToServer() {
//        val bizDetail = myRetrofit.makeService().getBizDetail(region = region, bizName = name)
//        bizDetail.enqueue(object: Callback<ModelBizDetail> {
//            override fun onFailure(call: Call<ModelBizDetail>, t: Throwable) {
//                Log.i("Detail Error", t.message!!)
//            }
//
//            override fun onResponse(call: Call<ModelBizDetail>, response: Response<ModelBizDetail>) {
//                try {
//                    modelBizDetail = response.body()!!
//                    item = ModelDetailList(modelBizDetail.items.telNum, modelBizDetail.items.address, modelBizDetail.items.roadAddress, modelBizDetail.items.monthlyVisits)
//
//                    initTempTexts()
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        })
//    }

    private fun setDataListener() {
        AdminData.setOnGetDataListener(this)
    }

    private fun getBizDetail() {
        AdminData.getBizDetail(region, name)
    }

    override fun getData(data: Any?) {
        modelBizDetail = data as ModelBizDetail?
        item = ModelDetailList(modelBizDetail!!.items.address, modelBizDetail!!.items.roadAddress, modelBizDetail!!.items.monthlyVisits, modelBizDetail!!.items.detailUrl)
        initTexts()
    }

//    @SuppressLint("SetTextI18n")
    private fun initTexts() {
    val date = item.monthlyVisits[0].date.split("-")

    val tvVisitcount = findViewById<TextView>(R.id.detail_visitcount)
    val tvName = findViewById<TextView>(R.id.detail_name)
    val tvType = findViewById<TextView>(R.id.detail_type)
    val tvRoadAddress = findViewById<TextView>(R.id.detail_roadAddress)
    val tvAddress = findViewById<TextView>(R.id.detail_address)
    val tvAvgCost = findViewById<TextView>(R.id.detail_avgCost)
    val wvWebView = findViewById<WebView>(R.id.detail_webview)

    tvVisitcount.text = visitcount + "회"
    tvName.text = name
    tvType.text = type
    if (item.roadAddress != null) {
        tvRoadAddress.text = item.roadAddress
    } else {
        tvRoadAddress.visibility = View.GONE
    }
    if (item.address != null) {
        tvAddress.text = "지번: " + item.address
    } else {
        tvAddress.visibility = View.GONE
    }
    tvAvgCost.text = "1인당 평균 " + avgCost.toString() + "원 사용"
    wvWebView.settings.javaScriptEnabled = true
    if (item.detailUrl != null) {
        wvWebView.loadUrl(item.detailUrl)
    } else {
        wvWebView.visibility = View.GONE
    }
    }

//        if (item.telNum != null) {
//            tvTelNum.text = "전화번호: "+item.telNum
//        } else {
//            tvTelNum.visibility = View.GONE
//        }
//        if (item.bizHour != null) {
//            tvBizHour.text = "영업시간: "+item.bizHour
//        } else {
//            tvBizHour.visibility = View.GONE
//        }


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
        val max: Int? = item.monthlyVisits.maxBy{it.count}?.count


        detail_chart.axisLeft.setDrawLabels(false)
        detail_chart.xAxis.setDrawGridLines(false)
        detail_chart.axisLeft.setDrawGridLines(false)
        detail_chart.axisLeft.setDrawAxisLine(false)
        detail_chart.axisRight.setDrawGridLines(false)
//        detail_chart.xAxis.setDrawLabels(false)
        detail_chart.xAxis.setDrawAxisLine(false)
        detail_chart.setTouchEnabled(false)

        xAxis.apply {
//            isEnabled = false
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 9f
//            setDrawGridLines(false)
            granularity = 1f
            axisMinimum = 0f
            isGranularityEnabled = true
        }
        detail_chart.apply {
            axisRight.isEnabled = false
            if (max != null) {
                axisLeft.axisMaximum = max.toFloat() + 1
            }
            legend.apply {
                isEnabled = false
                textSize = 14f
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
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
//                val date = item.monthlyVisits[i].date.split("-")
//                val dateFloat = "${date[0]}.${date[1]}".toFloat()
//                println(dateFloat)
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

        val dateList = mutableListOf<String>()
        for (i in item.monthlyVisits.indices) {
            val date = item.monthlyVisits[i].date.split("-")
            val dateString = "${date[0].substring(2)}년${date[1]}월"
            println(dateString)
            dateList.add(dateString)
        }
        println(dateList.toString())
        val labels = dateList
        detail_chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "월별 방문회수") //label: count
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