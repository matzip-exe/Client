package com.example.matzip_exe.utils

import android.util.Log
import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.interfaces.GetDataListener

class DataSynchronized() {
    private val myRetrofit = MyRetrofit()
    private var mData: Any? = null
    private lateinit var getDataListener: GetDataListener

    fun setOnGetDataListener(listener: GetDataListener){
        this.getDataListener = listener
    }

    fun initToken(){
        dataThread(runToken())
    }

    fun getRegion(){
        dataThread(runRegion())
    }

    fun getBizList(region: String, filter: String, since: Int, step: Int, lat: Double?, lng: Double?){
        dataThread(runBizList(region, filter, since, step, lat, lng))
    }

    fun getBizDetail(region: String, bizName: String){
        dataThread(runBizDetail(region, bizName))
    }

    private fun dataThread(type: Runnable){
        val thread = Thread(type)
        thread.start()
        thread.join()
        getDataListener.getData(mData)
    }

    private inner class runToken: Runnable{
        override fun run() {
            val callToken = myRetrofit.makeService().initAuth(Auth.userAgent)
            try{
                val response = callToken.execute()
                Auth.token = response.body()!!.token
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private inner class runRegion: Runnable{
        override fun run() {
            val callRegion = myRetrofit.makeService().checkRegion(Auth.userAgent, Auth.token)
            try {
                val response = callRegion.execute()
                mData = response.body()
                if (response.headers().get("x-access-token") != null){
                    Auth.token = response.headers().get("x-access-token")!!
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }

        }
    }

    private inner class runBizList(private val region: String, private val filter: String,
                                   private val since: Int, private val step: Int,
                                   private val lat: Double?, private val lng: Double?): Runnable{
        override fun run() {
            val callBizList = myRetrofit.makeService().getBizList(Auth.userAgent, Auth.token, region, filter, since, step, lat, lng)
            try {
                val response = callBizList.execute()
                mData = response.body()
                if (response.headers().get("x-access-token") != null){
                    Auth.token = response.headers().get("x-access-token")!!
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    private inner class runBizDetail(private val region: String, private val bizName: String): Runnable {
        override fun run() {
            val callBizDetail = myRetrofit.makeService().getBizDetail(Auth.userAgent, Auth.token, region, bizName)
            try {
                mData = callBizDetail.execute().body()
            }
            catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
//    Detail 부분 inner class 로 추가

}