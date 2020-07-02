package com.example.matzip_exe.activities

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.MatZipListAdapter
import com.example.matzip_exe.model.ModelMatZipList
import com.example.matzip_exe.utils.CheckLocation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_matzip_list.*

class MatzipList : AppCompatActivity() {
    private lateinit var area: String
    private lateinit var manager: LinearLayoutManager
    private lateinit var adaptermatziplist: MatZipListAdapter
    private val item = ArrayList<ModelMatZipList>()
    private var userLocation: Location? = null
    private lateinit var checkLocation: CheckLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matzip_list)

        area = intent.getStringExtra("area")!!

        init()
    }

    override fun onStart() {
        super.onStart()

        for (i in 1..10){
            item.add(ModelMatZipList("1", 1, "가나다라마바", "12.123km", "40"))
        }
    }

    private fun init(){
        initToolbars()
        initRecycle()
        initTabs()
        initLocation()
    }

    private fun initRecycle(){
        recycle_matziplist.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        recycle_matziplist.layoutManager = manager
        adaptermatziplist = MatZipListAdapter(item)
        recycle_matziplist.adapter = adaptermatziplist

        recycle_matziplist.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val tempManager = recyclerView.layoutManager as LinearLayoutManager
                val lastViewPosition = tempManager.findLastCompletelyVisibleItemPosition()
                val itemSize = recyclerView.adapter!!.itemCount


//                if문에서 데이터를 받아오면서도 부드럽게 움직일 수 있는 값을 한 번 찾아보는 것이 좋을 듯 하다
                if (itemSize - lastViewPosition <= 5){
                    for (i in 1..5){
                        item.add(ModelMatZipList("2", 1, "고노도로모보", "12.123km", "40"))
                        //리스너 등록해서 notifyDataSetChanged해야할까?
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
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
                        //방문순 정렬 default
                    }
                    1->{
                        //거리순 정렬
                    }
                    2->{
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

    private fun requetToServer(){

    }
}
