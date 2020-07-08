package com.example.matzip_exe.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

class CheckLocation(val context: Context)/*: LocationListener*/{
    private lateinit var userLocationManager: LocationManager
    private var location: Location? = null

    fun getLocation(): Location?{
        try{
            userLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val GPS = userLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val NetWork = userLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            Log.i("Check", "GPS:${GPS}, NetWork:${NetWork}")
            //getLastKnownLocation 사용할려면 권한이 있는지 확인해야함
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if(GPS){
                    location = userLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    Log.i("GPS", location.toString())
                }

                if (NetWork && location == null){
                    location = userLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    Log.i("NetWork", location.toString())
                    Toast.makeText(context, "NetWork ${location.toString()}", Toast.LENGTH_LONG).show()

                }
            }
            else{
                //팝업으로 0. 거리계산 용도임을 알려줌 1. 권한을 주었는지 2. 설정에서 위치를 활성화 했는지 알려주기
                location = null
            }
        }
        catch (e:Exception){
            Log.i("error", e.message)
            e.printStackTrace()
        }

        return location
    }
//    override fun onLocationChanged(location: Location?) {
//
//    }
//
//    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//
//    }
//
//    override fun onProviderEnabled(provider: String?) {
//
//    }
//
//    override fun onProviderDisabled(provider: String?) {
//
//    }

//    fun stopCheckLocation(){
//        userLocationManager.removeUpdates(this)
//    }
}