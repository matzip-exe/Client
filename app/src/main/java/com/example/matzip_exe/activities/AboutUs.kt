package com.example.matzip_exe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.RecycleAboutUsAdapter
import com.example.matzip_exe.databinding.ActivityAboutUsBinding
import com.example.matzip_exe.model.ModelAboutUsJson
import com.example.matzip_exe.model.ModelRecycleAboutUs
import com.google.gson.Gson

class AboutUs : AppCompatActivity() {
    private lateinit var activityAboutUsBinding: ActivityAboutUsBinding

    private val item = ArrayList<ModelRecycleAboutUs>()
    private lateinit var manager: LinearLayoutManager
    private lateinit var recycleAboutUsAdapter: RecycleAboutUsAdapter

    private lateinit var aboutUsJson: ModelAboutUsJson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAboutUsBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        init()
    }

    private fun init(){
        toolbarInit()
        getJson()
        recycleInit()
    }

    private fun toolbarInit(){
        setSupportActionBar(activityAboutUsBinding.toolbarAboutus)
        supportActionBar!!.title = getString(R.string.AboutUs)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun getJson(){
        try{
            val gson = Gson()
            val inputStream = this.assets.open("json/AboutUs.json")
            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            aboutUsJson = gson.fromJson(json, ModelAboutUsJson::class.java)
            itemInit()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun itemInit(){
        for (i in aboutUsJson.items){
            item.add(ModelRecycleAboutUs(i.Position, i.Name, i.Email))
        }
    }

    private fun recycleInit(){
        activityAboutUsBinding.recycleAboutus.setHasFixedSize(true)
        manager = LinearLayoutManager(this)
        activityAboutUsBinding.recycleAboutus.layoutManager = manager
        recycleAboutUsAdapter = RecycleAboutUsAdapter(item)
        activityAboutUsBinding.recycleAboutus.adapter = recycleAboutUsAdapter
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
