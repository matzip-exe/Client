package com.example.matzip_exe.activities

import android.Manifest
import android.app.Activity
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
import com.example.matzip_exe.interfaces.CheckLocationSettingListener
import com.example.matzip_exe.interfaces.GetDataListener
import com.example.matzip_exe.interfaces.NetworkConnectedListener
import com.example.matzip_exe.model.ModelToken
import com.example.matzip_exe.receivers.NetworkReceiver
import com.example.matzip_exe.utils.Auth
import com.example.matzip_exe.utils.DataSynchronized
import com.example.matzip_exe.utils.RequestLocationSetting
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

class Splash : AppCompatActivity(), NetworkConnectedListener, GetDataListener, CheckLocationSettingListener {
    private val receiver = NetworkReceiver()
    private val GPS_CODE = 3173
    private val CODE_LOCATION = 707
    private var GPScheck = false

    private val AdminData = DataSynchronized()
    private var modelToken: ModelToken? = null
    private lateinit var requestLocationSetting: RequestLocationSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    private fun init(){
        setDataListener()
        initToken()
        checkPermission()
        setCheckLocationSettingListener()
        receiver.setOnNetworkListener(this)
    }

    private fun setDataListener(){
        AdminData.setOnGetDataListener(this)
    }

    private fun setCheckLocationSettingListener(){
        requestLocationSetting = RequestLocationSetting(this)
        requestLocationSetting.setCheckLocationSettingListener(this)
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
        if (GPScheck){
            requestLocationSetting.requestGpsSettingChange()
        }
        else{
            moveToMain()
        }
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            GPS_CODE->{
                GPScheck = resultCode == Activity.RESULT_OK
            }
            CODE_LOCATION->{
                if(resultCode == Activity.RESULT_OK){
                    moveToMain()
                }else {
                    Toast.makeText(this, "거리 계산을 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    moveToMain()
                }
            }

        }
    }

    override fun isSetted() {
        moveToMain()
    }

    private fun moveToMain(){
        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
