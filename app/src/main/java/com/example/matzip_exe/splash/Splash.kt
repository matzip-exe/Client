package com.example.matzip_exe.splash

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matzip_exe.R
import com.example.matzip_exe.Utils.NetworkReceiver

class Splash : AppCompatActivity() {
    private lateinit var receiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        receiver = NetworkReceiver()
        this.registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()

        this.unregisterReceiver(receiver)
    }
}
