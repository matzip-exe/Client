package com.team_no_yes.matzip_exe.http

import com.team_no_yes.matzip_exe.detail.ModelBizDetail
import com.team_no_yes.matzip_exe.matziplist.ModelBizList
import com.team_no_yes.matzip_exe.main.ModelCheckRegion
import com.team_no_yes.matzip_exe.splash.ModelToken
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface InterfaceRetrofit {

    @GET("auth")
    fun initAuth(@Header("User-Agent") userAgent: String): Call<ModelToken>

    @GET("checkRegion")
    fun checkRegion(@Header("User-Agent") userAgent: String, @Header("x-access-token") token: String): Call<ModelCheckRegion>

    @Headers("User-Agent: TeamNoYes")
    @GET("getBizList")
    fun getBizList(@Header("User-Agent") userAgent: String, @Header("x-access-token") token: String, @Query("region") region: String, @Query("filter") filter: String, @Query("since") since: Int, @Query("step") step: Int, @Query("lat") lat: Double?, @Query("lng") lng: Double?): Call<ModelBizList>

    @Headers("User-Agent: TeamNoYes")
    @GET("getBizDetail")
    fun getBizDetail(@Header("User-Agent") userAgent: String, @Header("x-access-token") token: String, @Query("region") region: String, @Query("bizName") bizName: String): Call<ModelBizDetail>

}