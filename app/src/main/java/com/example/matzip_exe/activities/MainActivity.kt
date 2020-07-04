package com.example.matzip_exe.activities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentButton
import com.example.matzip_exe.fragments.FragmentMap
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.model.ModelCheckRegion
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

private const val NUM_PAGES = 2

class MainActivity : FragmentActivity() {
    private lateinit var mPager: ViewPager
    private lateinit var mTabs: TabLayout
    private lateinit var mCheckRegion: ModelCheckRegion
    private val myRetrofit = MyRetrofit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

    }

    private fun init(){
        setViewPager()
        setTabs()
        getRegion()
    }

    private fun setViewPager(){
        mPager = findViewById(R.id.viewpager_main)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mPager.adapter = pagerAdapter
    }

    private fun setTabs(){
        mTabs = findViewById(R.id.tabs_main)
        mTabs.setupWithViewPager(mPager)
        mTabs.getTabAt(0)!!.setIcon(R.drawable.ic_horizontal_rule_24px)
        mTabs.getTabAt(1)!!.setIcon(R.drawable.ic_horizontal_rule_24px)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){
        override fun getItem(position: Int): Fragment{
            when(position){
                0->{
                    return FragmentMap()
                }
                1->{
                    return FragmentButton()
                }
            }

            return FragmentMap()
        }

        override fun getCount(): Int = NUM_PAGES

    }

    private fun getRegion(){
        val Region = myRetrofit.makeService().checkRegion()

        Region.enqueue(object :Callback<ModelCheckRegion>{
            override fun onResponse(
                call: Call<ModelCheckRegion>,
                response: Response<ModelCheckRegion>
            ) {
                try{
                    mCheckRegion = response.body()!!

                    println(mCheckRegion.items[0].region)
                }
                catch (e: Exception){
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ModelCheckRegion>, t: Throwable) {
                Log.i("ERROR", t.message!!)
            }

        })
    }


}
