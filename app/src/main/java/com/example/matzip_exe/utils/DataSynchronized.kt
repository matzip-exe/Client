package com.example.matzip_exe.utils

import com.example.matzip_exe.http.MyRetrofit
import com.example.matzip_exe.interfaces.GetDataListener

class DataSynchronized() {
    private val myRetrofit = MyRetrofit()
    private var mData: Any? = null
    private lateinit var getDataListener: GetDataListener

    fun setOnGetDataListener(listener: GetDataListener){
        this.getDataListener = listener
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

    private inner class runRegion: Runnable{
        override fun run() {
            val callRegion = myRetrofit.makeService().checkRegion()
            mData = callRegion.execute().body()
        }
    }

    private inner class runBizList(private val region: String, private val filter: String,
                                   private val since: Int, private val step: Int,
                                   private val lat: Double?, private val lng: Double?): Runnable{
        override fun run() {
            val callBizList = myRetrofit.makeService().getBizList(region, filter, since, step, lat, lng)
            mData = callBizList.execute().body()
        }
    }

    private inner class runBizDetail(private val region: String, private val bizName: String): Runnable {
        override fun run() {
            val callBizDetail = myRetrofit.makeService().getBizDetail(region, bizName)
            mData = callBizDetail.execute().body()
        }
    }
//    Detail 부분 inner class 로 추가

}