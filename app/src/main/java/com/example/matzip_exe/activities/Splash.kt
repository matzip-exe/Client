package com.example.matzip_exe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.matzip_exe.R
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.receivers.NetworkReceiver

class Splash : AppCompatActivity(), NetworkConnectedListener {
    private val receiver = NetworkReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        receiver.setOnNetworkListener(this)
    }

    override fun onStart() {
        super.onStart()

        this.registerReceiver(receiver, receiver.getFilter())
    }

    override fun onStop() {
        super.onStop()

        this.unregisterReceiver(receiver)
    }

    override fun isConnected() {
        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    override fun isNotConnected() {
        Toast.makeText(this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
    }
}
