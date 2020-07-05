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
    fun getBizList(@Query("region") region: String, @Query("filter") filter: String, @Query("since") since: Int, @Query("step") step: Int, @Query("lat") lat: Double?, @Query("lng") lng: Double?): Call<ModelBizList>

    @GET("getBizDetail")
    fun getBizDetail(@Query("")): Call<ModelBizDetail>

}