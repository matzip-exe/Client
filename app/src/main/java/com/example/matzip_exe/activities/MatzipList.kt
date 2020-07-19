package com.example.matzip_exe.activities

import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.MatZipListAdapter
import com.example.matzip_exe.databinding.ActivityMatzipListBinding
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.model.ModelBizList
import com.example.matzip_exe.model.ModelMatZipList
import com.example.matzip_exe.utils.CheckLocation
import com.example.matzip_exe.utils.DataSynchronized
import com.google.android.material.tabs.TabLayout

class MatzipList : AppCompatActivity(), GetDataListener {
    private val AVG_COST = "avg_cost"
    private val VISITCOUNT = "visit_count"
    private val DISTANCE = "distance"

    private lateinit var activityMatzipListBinding: ActivityMatzipListBinding

    private val item = ArrayList<ModelMatZipList>()
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapterMatzipList: MatZipListAdapter

    private var modelBizList: ModelBizList? = null
    private val AdminData = DataSynchronized()

    private var userLocation: Location? = null
    private lateinit var checkLocation: CheckLocation

    private var filterPosition = 0
    private var callCount = 0

    private lateinit var area: String
    private lateinit var region: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMatzipListBinding = DataBindingUtil.setContentView(this, R.layout.activity_matzip_list)

        init()
    }

    private fun init(){
        initLocation()
        initIntent()
        initToolbar()
        initRecycle()
        initTabs()
        setDataListener()
        getBizList()
    }

    private fun initIntent(){
        area = intent.getStringExtra("area")!!
        region = intent.getStringExtra("region")!!
    }

    private fun initToolbar(){
        setSupportActionBar(activityMatzipListBinding.toolbarMatziplist)
        supportActionBar!!.title = area
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecycle(){
        activityMatzipListBinding.recycleMatziplist.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        activityMatzipListBinding.recycleMatziplist.layoutManager = manager
        adapterMatzipList = MatZipListAdapter(item, area, region)
        activityMatzipListBinding.recycleMatziplist.adapter = adapterMatzipList

        activityMatzipListBinding.recycleMatziplist.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val tempManager = recyclerView.layoutManager as LinearLayoutManager
                val lastViewPosition = tempManager.findLastCompletelyVisibleItemPosition()
                val itemSize = recyclerView.adapter!!.itemCount

                //서버가 빈 배열을 끝내면 요청을 멈춘다
                if (modelBizList?.items!!.isNotEmpty()){
                    recyclerView.post(Runnable {
                        if (itemSize - lastViewPosition <= 3){
                            callCount++
                            getBizList()
                        }
                    })
                }

            }
        })
    }

    private fun initTabs(){
        activityMatzipListBinding.tabMatziplist.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        changeTab(tab.position)
                    }
                    1->{
                        changeTab(tab.position)
                    }
                    2->{
                        changeTab(tab.position)
                    }
                }
            }
        })
    }

    private fun initLocation(){
        checkLocation = CheckLocation(this)
        userLocation = checkLocation.getLocation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDataListener(){
        AdminData.setOnGetDataListener(this)
    }

    private fun getBizList(){
        val filter = when(filterPosition){
            0->VISITCOUNT
            1->DISTANCE
            2->AVG_COST
            else->VISITCOUNT
        }

        AdminData.getBizList(region, filter, callCount*10, 10, userLocation?.latitude, userLocation?.longitude)
    }

    override fun getData(data: Any?) {
        modelBizList = data as ModelBizList?
        println("modelBizList ${modelBizList}")
        //빈 데이터 반환은 무조건 빈배열
        //서버 문제일 때는 null
        when {
            modelBizList == null ->{
                Toast.makeText(this, "잠시 후 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show()
            }
            modelBizList?.items == null -> {
                Toast.makeText(this, "잠시 후 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show()
            }
            modelBizList?.items!!.isEmpty() -> {

            }
            modelBizList?.items != null -> {
                for (i in modelBizList!!.items.indices){
                    item.add(ModelMatZipList((activityMatzipListBinding.recycleMatziplist.adapter!!.itemCount + 1).toString(),
                        modelBizList!!.items[i].bizType, modelBizList!!.items[i].bizName,
                        modelBizList!!.items[i].latlng, modelBizList!!.items[i].distance, modelBizList!!.items[i].visitCount,
                        modelBizList!!.items[i].avgCost, filterPosition))
                }

                adapterMatzipList.notifyDataSetChanged()
            }


        }
    }

    private fun changeTab(pos: Int){
        item.clear()
        callCount = 0
        filterPosition = pos
        getBizList()
        activityMatzipListBinding.recycleMatziplist.smoothScrollToPosition(0)
    }
}
