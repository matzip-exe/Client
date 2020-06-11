package com.example.matzip_exe.utils

import android.content.Context
import com.example.matzip_exe.model.ModelPolygon
import com.google.gson.Gson
import com.naver.maps.geometry.LatLng
import java.lang.Exception

class ParsingPolygon(private val context: Context) {
    private lateinit var parsejson: ModelPolygon

    fun getJson(): ModelPolygon{
        try{
            val gson = Gson()

            val inputStream = context.assets.open("json/Polygon.json")
            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            parsejson = gson.fromJson(json, ModelPolygon::class.java)

        }
        catch (e: Exception){
            e.printStackTrace()
        }

        return parsejson
    }
}