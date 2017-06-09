package com.wkw.hot.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.wkw.hot.R
import com.wkw.hot.view.base.ToolbarManager
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.layout_toolbar_view.*
import kotlinx.android.synthetic.main.view_loading.*


/**
 * Created by hzwukewei on 2017-6-9.
 */
class WebActivity : AppCompatActivity(), ToolbarManager {

    companion object {
        val URL: String = "URL"
        val TITLE: String = "TITLE"
    }

    override val toolbar by lazy {
        tool_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
        webView.setWebChromeClient(ChromeClient())
        webView.setWebViewClient(Client())
        val url = intent.getStringExtra(URL)
        val title = intent.getStringExtra(TITLE)
        url?.let {
            webView.loadUrl(url)
        }
        title?.let {
            toolbarTitle = title
            enableHomeAsUp {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        webView?.let {
            webView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        webView?.let {
            webView.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView?.let {
            webView.removeAllViews()
            webView.destroy()
        }
    }

    private inner class ChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress == 100) {
                rl_loading.visibility = View.GONE
            } else {
                rl_loading.visibility = View.VISIBLE
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    private inner class Client : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            if (url != null) view.loadUrl(url)
            return true
        }
    }
}