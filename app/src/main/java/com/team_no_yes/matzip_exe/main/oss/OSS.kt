package com.team_no_yes.matzip_exe.main.oss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.team_no_yes.matzip_exe.R
import com.team_no_yes.matzip_exe.databinding.ActivityOSSBinding
import com.google.gson.Gson

class OSS : AppCompatActivity() {
    private lateinit var activityOSSBinding: ActivityOSSBinding

    private val item = ArrayList<ModelRecycleOSS>()
    private lateinit var recycleOSSAdapter: RecycleOSSAdapter
    private lateinit var manager:LinearLayoutManager

    private lateinit var getOss: ModelGetOSS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityOSSBinding = DataBindingUtil.setContentView(this, R.layout.activity_o_s_s)

        init()
    }

    private fun init(){
        toolbarInit()
        getJson()
        recycleInit()
    }

    private fun toolbarInit(){
        setSupportActionBar(activityOSSBinding.toolbarOss)
        supportActionBar!!.title = getString(R.string.OSS)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getJson(){
        try{
            val gson = Gson()
            val inputStream = this.assets.open("json/OSS.json")
            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            getOss = gson.fromJson(json, ModelGetOSS::class.java)
            itemInit()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun itemInit(){
        for (i in getOss.items){
            item.add(
                ModelRecycleOSS(
                    i.Name,
                    i.Link,
                    i.Copyright,
                    i.License
                )
            )
        }
    }

    private fun recycleInit(){
        activityOSSBinding.recycleOss.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        activityOSSBinding.recycleOss.layoutManager = manager
        recycleOSSAdapter =
            RecycleOSSAdapter(item)
        activityOSSBinding.recycleOss.adapter = recycleOSSAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
