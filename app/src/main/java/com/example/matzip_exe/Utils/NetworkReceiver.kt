package com.example.matzip_exe.Utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.widget.Toast
import androidx.core.os.HandlerCompat.postDelayed
import com.example.matzip_exe.MainActivity

class NetworkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = conn.activeNetworkInfo

        when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> {
                Handler().postDelayed(Runnable {
                    Toast.makeText(context, "Wifi", Toast.LENGTH_SHORT).show()
                    val test = Intent(context, MainActivity::class.java)
                    context.startActivity(test)
                    val activity = context as Activity
                    activity.finish()
                }, 1000)
            }
            ConnectivityManager.TYPE_MOBILE -> {
                Handler().postDelayed(Runnable {
                    Toast.makeText(context, "Mobile", Toast.LENGTH_SHORT).show()
                    val test = Intent(context, MainActivity::class.java)
                    context.startActivity(test)
                    val activity = context as Activity
                    activity.finish()
                }, 1000)

            }
            else -> {
                Toast.makeText(context, "Please Connect NetWork", Toast.LENGTH_SHORT).show()
            }
        }
    }
}