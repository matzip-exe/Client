package com.example.matzip_exe.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {
    private val IP = "http://ec2-13-209-40-59.ap-northeast-2.compute.amazonaws.com:8080/user/"
    private lateinit var retrofit: Retrofit
    private lateinit var interfaceRetrofit: InterfaceRetrofit

    fun makeService():InterfaceRetrofit{
        retrofit = Retrofit.Builder().baseUrl(IP).addConverterFactory(GsonConverterFactory.create()).build()
        interfaceRetrofit = retrofit.create(InterfaceRetrofit::class.java)

        return interfaceRetrofit
    }


}