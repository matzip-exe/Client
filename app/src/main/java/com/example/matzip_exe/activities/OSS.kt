package com.example.matzip_exe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.RecycleOSSAdapter
import com.example.matzip_exe.databinding.ActivityOSSBinding
import com.example.matzip_exe.model.ModelOSSJson
import com.example.matzip_exe.model.ModelRecycleOSS
import com.google.gson.Gson
import org.json.JSONObject

class OSS : AppCompatActivity() {
    private lateinit var activityOSSBinding: ActivityOSSBinding

    private val item = ArrayList<ModelRecycleOSS>()
    private lateinit var recycleOSSAdapter: RecycleOSSAdapter
    private lateinit var manager:LinearLayoutManager

    private lateinit var ossJson: ModelOSSJson

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

            ossJson = gson.fromJson(json, ModelOSSJson::class.java)
            itemInit()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun itemInit(){
        for (i in ossJson.items){
            item.add(ModelRecycleOSS(i.Name, i.Link, i.Copyright, i.License))
        }
    }

    private fun recycleInit(){
        activityOSSBinding.recycleOss.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        activityOSSBinding.recycleOss.layoutManager = manager
        recycleOSSAdapter = RecycleOSSAdapter(item)
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
