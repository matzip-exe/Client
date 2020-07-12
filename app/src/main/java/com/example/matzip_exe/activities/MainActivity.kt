package com.example.matzip_exe.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.RecycleMainAdapter
import com.example.matzip_exe.databinding.ActivityMainBinding
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.model.ModelCheckRegion
import com.example.matzip_exe.model.ModelRecycleMain
import com.example.matzip_exe.utils.DataSynchronized
import org.json.JSONObject

class MainActivity : AppCompatActivity(), GetDataListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var regionJson: JSONObject

    private val item = ArrayList<ModelRecycleMain>()
    private lateinit var adapterRecycleMain: RecycleMainAdapter
    private lateinit var manager: GridLayoutManager

    private var modelCheckRegion: ModelCheckRegion? = null
    private val AdminData = DataSynchronized()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init(){
        toolbarInit()
        getRegionJson()
        recycleInit()
        setDataListener()
        getRegion()
    }

    private fun toolbarInit(){
        setSupportActionBar(activityMainBinding.toolbarMain)
    }

    private fun recycleInit(){
        activityMainBinding.recycleMain.setHasFixedSize(true)
        manager = GridLayoutManager(this, 3)
        activityMainBinding.recycleMain.layoutManager = manager
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

    private fun setDataListener(){
        AdminData.setOnGetDataListener(this)
    }

    private fun getRegion(){
        AdminData.getRegion()
    }

    override fun getData(data: Any?) {
        modelCheckRegion = data as ModelCheckRegion?

        when {
            modelCheckRegion == null ->{
                Toast.makeText(this, "잠시 후 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show()
                finish()
            }
            modelCheckRegion?.items == null -> {
                Toast.makeText(this, "잠시 후 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show()
            }
            modelCheckRegion?.items!!.isEmpty() -> {
                Toast.makeText(this, "잠시 후 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show()
            }
            modelCheckRegion?.items != null -> {
                for (i in modelCheckRegion!!.items.indices){
                    item.add(ModelRecycleMain(regionJson.getString(modelCheckRegion!!.items[i].region),
                        modelCheckRegion!!.items[i].isExist,
                        modelCheckRegion!!.items[i].region))
                }
            }
        }

        adapterRecycleMain.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val itemMain = menuInflater
        itemMain.inflate(R.menu.main_switch, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main_info ->{
                infoAlertDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun infoAlertDialog(){
        val builder = AlertDialog.Builder(this)

        builder.setItems(R.array.Info, object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when(which){
                    0->{
                        Toast.makeText(applicationContext, "About us", Toast.LENGTH_SHORT).show()
                    }
                    1->{
                        Toast.makeText(applicationContext, "오픈소스 라이선스", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        builder.setNegativeButton(getString(R.string.cancel), object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.dismiss()
            }
        })

        val mainAlertDialog = builder.create()
        mainAlertDialog.show()

    }
}
