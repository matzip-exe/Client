package com.example.matzip_exe.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import kotlinx.android.synthetic.main.activity_matzip_list.*

class Detail: AppCompatActivity() {
    private lateinit var visitcount: String
    private lateinit var name: String
    private lateinit var type: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        visitcount = intent.getStringExtra("visitcount")!!
        name = intent.getStringExtra("name")!!
        type = intent.getStringExtra("type")!!

        init()
    }

    private fun init() {
        initTempTexts()
    }

    private fun initTempTexts() {
        val tvVisitcount = findViewById<TextView>(R.id.detail_visitcount)
        val tvName = findViewById<TextView>(R.id.detail_name)
        val tvType = findViewById<TextView>(R.id.detail_type)

        tvVisitcount.text = visitcount
        tvName.text = name
        tvType.text = type
    }

}