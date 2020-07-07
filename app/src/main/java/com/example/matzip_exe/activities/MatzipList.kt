package com.example.matzip_exe.activities

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.MatZipListAdapter
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.model.ModelBizList
import com.example.matzip_exe.model.ModelMatZipList
import com.example.matzip_exe.utils.CheckLocation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_matzip_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MatzipList : AppCompatActivity() {
    private lateinit var area: String
    private lateinit var region: String
    private lateinit var manager: LinearLayoutManager
    private lateinit var adaptermatziplist: MatZipListAdapter
    private val item = ArrayList<ModelMatZipList>()
    private var userLocation: Location? = null
    private lateinit var checkLocation: CheckLocation
    private var filterPosition = 0
    private var callCount = 0
    private lateinit var modelBizList: ModelBizList
    private val myretrofit = MyRetrofit()
    private val AVG_COST = "avg_cost"
    private val VISITCOUNT = "visit_count"
    private val DISTANCE = "distance"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matzip_list)

        init()
    }

    override fun onStart() {
        super.onStart()

//        for (i in 1..10){
//            item.add(ModelMatZipList("1", 1, "가나다라마바", "12.123km", "40"))
//        }
    }

    private fun init(){
        initIntent()
        initToolbars()
        initRecycle()
        initTabs()
        initLocation()
        requestToServer()
    }

    private fun initIntent(){
        area = intent.getStringExtra("area")!!
        region = intent.getStringExtra("region")!!
    }

    private fun initRecycle(){
        recycle_matziplist.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        recycle_matziplist.layoutManager = manager
        adaptermatziplist = MatZipListAdapter(item, area, region)
        recycle_matziplist.adapter = adaptermatziplist

        recycle_matziplist.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val tempManager = recyclerView.layoutManager as LinearLayoutManager
                val lastViewPosition = tempManager.findLastCompletelyVisibleItemPosition()
                val itemSize = recyclerView.adapter!!.itemCount


//                if문에서 데이터를 받아오면서도 부드럽게 움직일 수 있는 값을 한 번 찾아보는 것이 좋을 듯 하다
                if (itemSize - lastViewPosition <= 3){
                    callCount ++

                    requestToServer()
//                    for (i in 1..5){
//                        item.add(ModelMatZipList("2", 1, "고노도로모보", "12.123km", "40"))
//                        //리스너 등록해서 notifyDataSetChanged해야할까?
//                        recyclerView.adapter!!.notifyDataSetChanged()
//                    }
                }

            }
        })
    }



    private fun initToolbars(){
        setSupportActionBar(toolbar_matziplist)
        supportActionBar!!.title = area
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTabs(){
        tab_matziplist.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->{
                        item.clear()
                        adaptermatziplist.notifyDataSetChanged()
                        callCount = 0
                        filterPosition = 0
                        requestToServer()
                        //방문순 정렬 default
                    }
                    1->{
                        item.clear()
                        adaptermatziplist.notifyDataSetChanged()
                        callCount = 0
                        filterPosition = 1
                        requestToServer()
                        //거리순 정렬
                    }
                    2->{
                        item.clear()
                        adaptermatziplist.notifyDataSetChanged()
                        callCount = 0
                        filterPosition = 2
                        requestToServer()
                        //금액순 정렬
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }


        })
    }

    private fun initLocation(){
        checkLocation = CheckLocation(this)
        userLocation = checkLocation.getLocation()
        Log.i("userLocation", "${userLocation?.latitude}, ${userLocation?.longitude}")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun requestToServer(){
        val filter = when(filterPosition){
            0 -> VISITCOUNT
            1 -> DISTANCE
            2 -> AVG_COST

            else -> VISITCOUNT
        }

        val bizList = myretrofit.makeService().getBizList(region, filter, callCount*10, 10,
            userLocation?.latitude, userLocation?.longitude)

        bizList.enqueue(object : Callback<ModelBizList>{
            override fun onFailure(call: Call<ModelBizList>, t: Throwable) {
                Log.i("ERROR", t.message!!)
            }

            override fun onResponse(call: Call<ModelBizList>, response: Response<ModelBizList>) {
                try{
                    modelBizList = response.body()!!

                    for (i in modelBizList.items.indices){
                        item.add(ModelMatZipList((i+1).toString(), 0, modelBizList.items[i].bizName, modelBizList.items[i].latlng, modelBizList.items[i].distance, modelBizList.items[i].visitCount))
                    }

                    adaptermatziplist.notifyDataSetChanged()

                    println("bizName${modelBizList.items[0].bizName}")
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        })


    }
}
