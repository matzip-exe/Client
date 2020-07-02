package com.example.matzip_exe.http

import com.example.matzip_exe.model.ModelBizDetail
import com.example.matzip_exe.model.ModelBizList
import com.example.matzip_exe.model.ModelCheckRegion
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface InterfaceRetrofit {
    @GET("checkRegion")
    fun checkRegion(): Call<ModelCheckRegion>

    @GET("getBizList")
    fun getBizList(/*파라미터 추가해야함*/): Call<ModelBizList>

    @GET("getBizDetail")
    fun getBizDetail(/*파라미터 추가해야함*/): Call<ModelBizDetail>

}