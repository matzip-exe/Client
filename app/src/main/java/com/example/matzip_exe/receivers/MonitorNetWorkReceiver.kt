package com.example.matzip_exe.receivers

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.widget.Toast

class MonitorNetWorkReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> {

            }
            ConnectivityManager.TYPE_MOBILE -> {

            }
            else -> {

            }
        }
    }
}
