package com.example.matzip_exe.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPhoneCall(private val activity: Activity) {
    val CALL_PHONE_CODE = 1996

    fun requestPermission(){
        if (ContextCompat.checkSelfPermission(activity.applicationContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)){
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_CODE)
            }
            else{
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_CODE)
            }
        }
    }

    
}