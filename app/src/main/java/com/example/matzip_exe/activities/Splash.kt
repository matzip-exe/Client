package com.example.matzip_exe.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.matzip_exe.R
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.receivers.NetworkReceiver

class Splash : AppCompatActivity(), NetworkConnectedListener {
    private val receiver = NetworkReceiver()
    private val GPS_CODE = 3173

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        checkPermission()
        receiver.setOnNetworkListener(this)
    }

    override fun onStart() {
        super.onStart()
//        this.registerReceiver(receiver, receiver.getFilter())
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            GPS_CODE->{
//                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)){
//                    Toast.makeText(this, "GPS 권한 부여됨", Toast.LENGTH_SHORT).show()
//                }
//                else{
//                    Toast.makeText(this, "GPS 권한 부여 안됨", Toast.LENGTH_SHORT).show()
//                }
                this.registerReceiver(receiver, receiver.getFilter())
                return
            }
        }
    }

    private fun checkPermission(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), GPS_CODE)
        }
        else{
            this.registerReceiver(receiver, receiver.getFilter())
        }

    }
}
