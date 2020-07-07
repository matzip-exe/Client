package com.example.matzip_exe.utils

import com.example.matzip_exe.http.MyRetrofit

class DataSynchronized() {
    private val myRetrofit = MyRetrofit()
    private var mData: Any? = null

    fun getRegion(): Any?{

        dataThread(runRegion())

        return mData
    }

//    fun getBizList(): Any?{
//
//    }
//
//    fun getBizDetail(): Any?{
//
//    }

    private fun dataThread(type: Runnable){
        val thread = Thread(type)
        thread.start()
        thread.join()
    }

    private inner class runRegion: Runnable{
        override fun run() {
            val callRegion = myRetrofit.makeService().checkRegion()
            mData = callRegion.execute().body()
        }
    }

//    private inner class runBizList: Runnable{
//        override fun run() {
//            val callBizList = myRetrofit.makeService().getBizList()
//            mData = callBizList.execute().body()
//        }
//    }

}