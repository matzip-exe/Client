package com.example.matzip_exe.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    private val IP = ""
    private lateinit var retrofit: Retrofit
    private lateinit var interfaceRetrofit: InterfaceRetrofit

    fun makeService():InterfaceRetrofit{
        retrofit = Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).build()
        interfaceRetrofit = retrofit.create(interfaceRetrofit::class.java)

        return interfaceRetrofit
    }


}