package com.example.matzip_exe.utils

import android.app.Activity
import com.example.matzip_exe.interfaces.CheckLocationSettingListener
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

class RequestLocationSetting(private val activity:Activity) {
    private val CODE_LOCATION = 707
    private lateinit var checkLocationSettingListener:CheckLocationSettingListener

    fun setCheckLocationSettingListener(listener: CheckLocationSettingListener){
        checkLocationSettingListener = listener
    }

    fun requestGpsSettingChange() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val result = LocationServices.getSettingsClient(activity).checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {
                it.getResult(ApiException::class.java)// 설정이 안되어있는경우 catch로 빠짐.
                checkLocationSettingListener.isSetted()
                //이미 설정되어있는 경우 catch로 빠지지 않음.
                //startConfirmLogic
            } catch (e: ApiException) {
                when(e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvable = e as ResolvableApiException
                        resolvable.startResolutionForResult(activity, CODE_LOCATION)
                    }
                }
            }
        }
    }
}