package com.example.matzip_exe.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matzip_exe.R
import com.example.matzip_exe.receivers.LogoNetWorkReceiver
import com.example.matzip_exe.utils.DetectNetWorkConnected

class Splash : AppCompatActivity() {
    private val receiver = DetectNetWorkConnected()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        receiver.setContext(this)
        receiver.setReceiver(LogoNetWorkReceiver())
        receiver.register()
    }

    override fun onStop() {
        super.onStop()

        receiver.unregister()
    }

}
