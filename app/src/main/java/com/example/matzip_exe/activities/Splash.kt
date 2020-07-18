package com.example.matzip_exe.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.matzip_exe.R
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.model.ModelToken
import com.example.matzip_exe.receivers.NetworkReceiver
import com.example.matzip_exe.utils.Auth
import com.example.matzip_exe.utils.DataSynchronized

class Splash : AppCompatActivity(), NetworkConnectedListener, GetDataListener {
    private val receiver = NetworkReceiver()
    private val GPS_CODE = 3173

    private val AdminData = DataSynchronized()
    private var modelToken: ModelToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init(){
        setDataListener()
        initToken()
        checkPermission()
        receiver.setOnNetworkListener(this)
    }

    private fun setDataListener(){
        AdminData.setOnGetDataListener(this)
    }

    private fun initToken(){
        AdminData.initToken()
    }

    override fun onStop() {
        super.onStop()

        this.unregisterReceiver(receiver)
    }

    override fun getData(data: Any?) {
        modelToken = data as ModelToken?

        if (modelToken?.token != null){
            Auth.token = modelToken!!.token
        }
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
        //ContextCompat.checkSelfPermission은 앱에 사용 가능한 권한을 요청했는지 여부를 확인하는데 사용된다.
        //ActivityCompat.checkSelfPermission은 권한을 요청하는데 사용된다.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재요청 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), GPS_CODE)
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), GPS_CODE)
            }

        }
        else{
            this.registerReceiver(receiver, receiver.getFilter())
        }

    }
}
