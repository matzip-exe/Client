package com.team_no_yes.matzip_exe.utils

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class CustomWebViewClient: WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null) {
            Log.i("url", url)
            if(url.startsWith("tel:")){
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                view!!.context.startActivity(intent)
                return true
            }
        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)

        view!!.loadUrl("javascript:if (typeof(document.getElementsByClassName('order_flow_wrap')[0]) != 'undefined' && document.getElementsByClassName('order_flow_wrap')[0] != null){document.getElementsByClassName('order_flow_wrap')[0].style.display = 'none';} " +
                "if (typeof(document.getElementsByClassName('_3EHfk')[3]) != 'undefined' && document.getElementsByClassName('_3EHfk')[3] != null){document.getElementsByClassName('_3EHfk')[3].style.display = 'none';} void 0")
    }
}