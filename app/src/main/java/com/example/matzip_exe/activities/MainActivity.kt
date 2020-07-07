package com.example.matzip_exe.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.RecycleMainAdapter
import com.example.matzip_exe.databinding.ActivityMainBinding
import com.example.matzip_exe.model.ModelCheckRegion
import com.example.matzip_exe.model.ModelRecycleMain
import com.example.matzip_exe.utils.DataSynchronized
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var regionJson: JSONObject

    private val item = ArrayList<ModelRecycleMain>()
    private lateinit var adapterRecycleMain: RecycleMainAdapter
    private lateinit var manager: GridLayoutManager

    private var modelCheckRegion: ModelCheckRegion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init(){
        getRegionJson()
        getRegion()
        recycleInit()
    }

    private fun recycleInit(){
        activityMainBinding.recycleMain.setHasFixedSize(true)
        manager = GridLayoutManager(this, 3)
        activityMainBinding.recycleMain.layoutManager = manager
        if(modelCheckRegion != null){
            for (i in modelCheckRegion!!.items.indices){
                item.add(ModelRecycleMain(regionJson.getString(modelCheckRegion!!.items[i].region),
                    modelCheckRegion!!.items[i].isExist,
                    modelCheckRegion!!.items[i].region))
            }
        }

        adapterRecycleMain = RecycleMainAdapter(item)
        activityMainBinding.recycleMain.adapter = adapterRecycleMain
    }

    private fun getRegionJson(){
        try{

            val inputStream = this.assets.open("json/Region.json")
            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            regionJson = JSONObject(json)
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getRegion(){
        modelCheckRegion = DataSynchronized().getRegion() as ModelCheckRegion?
    }
}
