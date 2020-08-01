package com.example.matzip_exe.utils

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient: WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null) {
            if(url.startsWith("tel:")){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                view!!.context.startActivity(intent)
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }
}