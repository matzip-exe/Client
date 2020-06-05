package com.example.matzip_exe.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.matzip_exe.interfaces.NetworkConnectedListener

class NetworkReceiver : BroadcastReceiver() {
    private lateinit var networkListener: NetworkConnectedListener
    private val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    override fun onReceive(context: Context, intent: Intent) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI or ConnectivityManager.TYPE_MOBILE-> {
                networkListener.isConnected()
            }
            else -> {
                networkListener.isNotConnected()
            }
        }
    }

    fun setOnNetworkListener(listener: NetworkConnectedListener){
        this.networkListener = listener
    }

    fun getFilter():IntentFilter{
        return filter
    }
}
