package com.example.matzip_exe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matzip_exe.R
import com.example.matzip_exe.fragments.FragmentButton
import com.example.matzip_exe.fragments.FragmentMap
import com.example.matzip_exe.receivers.MonitorNetWorkReceiver
import com.example.matzip_exe.utils.DetectNetWorkConnected
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val receiver = DetectNetWorkConnected()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.main_frame, FragmentMap()).commit()

        bnav_main.setOnNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.main_mapSwitch ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, FragmentMap()).commit()
                    true
                }
                R.id.main_buttonSwitch ->{
                    supportFragmentManager.beginTransaction().replace(R.id.main_frame, FragmentButton()).commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()

        receiver.setContext(this)
        receiver.setReceiver(MonitorNetWorkReceiver())
        receiver.register()
    }

    override fun onStop() {
        super.onStop()

        receiver.unregister()
    }
}
