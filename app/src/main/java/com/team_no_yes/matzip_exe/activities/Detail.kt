package com.team_no_yes.matzip_exe.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_no_yes.matzip_exe.R
import com.team_no_yes.matzip_exe.adapter.DetailRecommendationAdapter
import com.team_no_yes.matzip_exe.fragments.FragmentDetail
import com.team_no_yes.matzip_exe.interfaces.GetDataListener
import com.team_no_yes.matzip_exe.model.ModelBizDetail
import com.team_no_yes.matzip_exe.model.ModelDetailList
import com.team_no_yes.matzip_exe.model.ModelRecommendation
import com.team_no_yes.matzip_exe.utils.DataSynchronized
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.appbar.AppBarLayout
import com.naver.maps.map.MapFragment
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.abs


class Detail : AppCompatActivity(), GetDataListener {
    private lateinit var visitcount: String
    private lateinit var name: String
    private lateinit var type: String
    private lateinit var area: String
    private lateinit var region: String
    private var locatex: Double = 0.0
    private var locatey: Double = 0.0
    private var modelBizDetail: ModelBizDetail? = null
    private lateinit var item: ModelDetailList
    private val AdminData = DataSynchronized()
    private var avgCost: Int = 0
    private val recycleItem = ArrayList<ModelRecommendation>()
    private lateinit var manager:LinearLayoutManager
    private lateinit var adapter: DetailRecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        name = intent.getStringExtra("name")!!
        type = intent.getStringExtra("type")!!
        area = intent.getStringExtra("area")!!
        region = intent.getStringExtra("region")!!

        init()
    }

    private fun init() {
        initToolbar()
        initRecyclerView()

        setDataListener()
        getBizDetail()
        fragmentDetail(name, locatex, locatey)
        if (item.monthlyVisits.size > 1) {
            initChart()
            detail_noChart.visibility = View.GONE
        } else {
            detail_chart.visibility = View.GONE
        }
    }

    private fun setDataListener() {
        AdminData.setOnGetDataListener(this)
    }

    private fun getBizDetail() {
        AdminData.getBizDetail(region, name)
    }

    override fun getData(data: Any?) {
        modelBizDetail = data as ModelBizDetail?
        item = ModelDetailList(
            modelBizDetail!!.items.monthlyVisits,
            modelBizDetail!!.items.avgCost,
            modelBizDetail!!.items.latlng,
            modelBizDetail!!.items.detailUrl,
            modelBizDetail!!.items.visitCount,
            modelBizDetail!!.items.recommendations
        )
        avgCost = item.avgCost
        locatex = item.latlng.x
        locatey = item.latlng.y
        visitcount = item.visitCount.toString()
        initTexts()
        Log.i("item", item.toString())
        for (i in item.recommendations!!){
            i.bizName?.let { i.bizType?.let { it1 -> ModelRecommendation(it, it1) } }?.let { recycleItem.add(it) }
            adapter.notifyDataSetChanged()
        }
    }

    private fun initTexts() {
        detail_button.setOnClickListener {
            if(item.detailUrl != null) {
                val intent = Intent(this, DetailWeb::class.java)
                intent.putExtra("url", item.detailUrl)
                intent.putExtra("area", area)
                this.startActivity(intent)
            } else {
                Toast.makeText(this, "현재 상세 정보 이용이 어렵습니다.\n서비스 이용에 불편을 드려 죄송합니다.", Toast.LENGTH_LONG).show()
            }
        }

        detail_visitcount.text = visitcount
        detail_name.text = name
        detail_type.text = type
        detail_avgCost.text = avgCost.toString()
    }

    private fun fragmentDetail(name: String, locatex: Double, locatey: Double) {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.detail_map_layout) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.detail_map_layout, it).commit()
            }
        mapFragment.getMapAsync(FragmentDetail(name, locatex, locatey))
    }

    private fun initToolbar() {
        setSupportActionBar(detail_toolbar)
        supportActionBar!!.title = null
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        detail_appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                detail_toolbar.visibility = View.VISIBLE
                supportActionBar!!.title = area
            }
            else if (abs(verticalOffset) - appBarLayout.totalScrollRange < 0 && abs(verticalOffset) > 0){
                detail_toolbar.visibility = View.INVISIBLE
                detail_collapsingtoolbar.title = null
                linear_detail_recommendation.visibility = View.VISIBLE
                initRecommendationSentence()
            }
            else {
                detail_toolbar.visibility = View.INVISIBLE
                detail_collapsingtoolbar.title = null
            }
        })
    }

    private fun initChart() {
        val xAxis: XAxis = detail_chart.xAxis
        val max: Int? = item.monthlyVisits.maxBy { it.count }?.count


        detail_chart.axisLeft.setDrawLabels(false)
        detail_chart.xAxis.setDrawGridLines(false)
        detail_chart.axisLeft.setDrawGridLines(false)
        detail_chart.axisLeft.setDrawAxisLine(false)
        detail_chart.axisRight.setDrawGridLines(false)
        detail_chart.xAxis.setDrawAxisLine(false)
        detail_chart.setTouchEnabled(false)

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 9f
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
                data.addEntry(
                    Entry(
                        set.entryCount.toFloat(),
                        item.monthlyVisits[i].count.toFloat()
                    ), 0
                )
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

    @SuppressLint("WrongConstant")
    private fun initRecyclerView(){
        recycle_detail.setHasFixedSize(true)
        manager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        recycle_detail.layoutManager = manager
        adapter = DetailRecommendationAdapter(recycleItem, area, region)
        recycle_detail.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecommendationSentence(){
        val format = getString(R.string.recommendataion_sentence)
        val whatType = adapter.getTypeString(this)
        val sentence = String.format(format, area, whatType)

        val spannableString = SpannableString(sentence)
        val start = sentence.indexOf(whatType)
        val end = start + whatType.length

        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#21538E")),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text_detail_recommendation_sentence.text = spannableString
    }
}