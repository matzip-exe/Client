package com.example.matzip_exe.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.matzip_exe.R
import com.example.matzip_exe.adapter.ButtonAdapter
import com.example.matzip_exe.model.ModelButton
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_button.*
import org.json.JSONObject
import java.lang.Exception

class FragmentButton: Fragment() {
    private val item = ArrayList<ModelButton>()
    private lateinit var adapterButton: ButtonAdapter
    private lateinit var manager: GridLayoutManager
    private lateinit var regionJson: JSONObject

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init(){
        getRegionJson()
        init_recyclerview()
    }

    private fun init_recyclerview(){
        recycle_button.setHasFixedSize(true)
        manager = GridLayoutManager(context,2)
        recycle_button.layoutManager = manager

        //차후에 서버가 넘겨주는 데이터로 전환해야한다.

        for (i in 1..26){
            item.add(ModelButton("서울시", true))
        }

        adapterButton = ButtonAdapter(item, regionJson)
        recycle_button.adapter = adapterButton

    }

    private fun getRegionJson(){
        try{
            val gson = Gson()

            val inputStream = context!!.assets.open("json/Region.json")
            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            regionJson = JSONObject(json)

        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
}