package com.demo.myadsmanage.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.demo.myadsmanage.AdsManage
import com.demo.myadsmanage.InterFace.OnSplachAds
import com.demo.myadsmanage.R

class SplachActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        AdsManage.ActivityBuilder().Splash_Init(this,object : OnSplachAds {
            override fun OnNextAds() {
                Handler().postDelayed({
                    startActivity(Intent(this@SplachActivity,MainActivity::class.java))
                }, 2000)

            }

            override fun OnError() {

            }
        })
    }
}