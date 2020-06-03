package com.example.matzip_exe.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager

class DetectNetWorkConnected{
    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
    private lateinit var receiver: BroadcastReceiver
    private lateinit var context: Context

    fun setContext(context: Context){
        this.context = context
    }

    fun setReceiver(receiver: BroadcastReceiver){
        this.receiver = receiver
    }

    fun register(){
        context.registerReceiver(receiver, filter)
    }

    fun unregister(){
        context.unregisterReceiver(receiver)
    }

}