package com.example.matzip_exe.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.matzip_exe.R
import com.example.matzip_exe.utils.CustomWebViewClient
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_web.*

class DetailWeb: AppCompatActivity() {
    private var url: String? = null
    private var area: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_web)

        url = intent.getStringExtra("url");
        area = intent.getStringExtra("area")

        init()
    }

    private fun init() {
        initToolbar()
        setWebView()
    }

    private fun initToolbar() {
        setSupportActionBar(detailweb_toolbar)
        supportActionBar!!.title = area
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setWebView() {
        detailweb_webview.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.domStorageEnabled = true
            webViewClient = CustomWebViewClient()
        }
        if (url != null) {
            detailweb_webview.loadUrl(url)
        } else {
            detailweb_webview.visibility = View.GONE
//            NEED FIX. TO NOTIFICATION.
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(detailweb_webview.canGoBack()) {
            detailweb_webview.goBack()
            println("can go back")
        } else {
            println("finish")
            finish();
        }
    }
}