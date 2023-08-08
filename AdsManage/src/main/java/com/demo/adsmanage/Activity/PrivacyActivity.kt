package com.demo.adsmanage.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.demo.adsmanage.Commen.Constants.mPrivacyPolicyURL
import com.demo.adsmanage.R
import com.demo.adsmanage.basemodule.BaseActivity
import com.demo.adsmanage.databinding.ActivityPrivacyPolicyBinding
import com.demo.adsmanage.helper.isOnline

class PrivacyActivity : BaseActivity() {

    private var mPrivacyPolicyActivity: PrivacyActivity?=null
    override fun getActivityContext(): AppCompatActivity {
        return this@PrivacyActivity
    }
    val binding:ActivityPrivacyPolicyBinding by lazy {
        ActivityPrivacyPolicyBinding.inflate(layoutInflater)
    }
    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        mPrivacyPolicyActivity = this@PrivacyActivity
        if (!isOnline) {
            binding.ctOffline.visibility=View.VISIBLE
            binding.webView.visibility=View.GONE
        }else{
            binding.ctOffline.visibility=View.GONE
            binding.webView.visibility=View.VISIBLE
        }
        binding.ctInternetDisable.setOnClickListener {

            if (!isOnline) {
                binding.ctOffline.visibility=View.VISIBLE
                binding.webView.visibility=View.GONE
                Toast.makeText(
                    this,
                    "Please check internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                startWebView()
                binding.ctOffline.visibility=View.GONE
                binding.webView.visibility=View.VISIBLE
            }
        }
        startWebView()
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView() {
        val url=mPrivacyPolicyURL
        val settings: WebSettings = binding.webView.settings
        settings.javaScriptEnabled = true
        binding.pdMdialog.visibility=View.VISIBLE
        binding.webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                if (binding.pdMdialog.isVisible) {
                    binding.pdMdialog.visibility=View.GONE
                }
            }

            @Deprecated("Deprecated in Java")
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }
        }
        binding.webView.loadUrl(url!!)
    }
}