package com.demo.myadsmanage

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.demo.adsmanage.AdsClass.AppOpenAds.loadAppOpenAd
import com.demo.adsmanage.AdsManage
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isAdsShowing
import com.demo.adsmanage.InterFace.OnAppOpenShowAds
import com.demo.adsmanage.helper.MySharedPreferences
import com.demo.adsmanage.helper.MySharedPreferences.AD_AppOpen
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import java.util.Arrays

class myApplication : Application(), LifecycleObserver,
    Application.ActivityLifecycleCallbacks {


    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        AdsManage.ActivityBuilder().ApplicationCall(this)
        AudienceNetworkAds.initialize(this);
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        AdSettings.addTestDevice("369fac27-5a40-4d91-9e86-bc0a7d221977")
        AdSettings.addTestDevice("c12a6da6-0672-4112-97c6-078dc7774b7b")
        AdSettings.addTestDevice("2a2fe541-44d6-40b2-8db8-6b6c30bb1946")
        AdSettings.addTestDevice("e09c375f-c62a-4cfe-a545-abb4fbfd8e3b")
        AdSettings.addTestDevice("c3a7427f-18eb-4349-bffd-d3c8e46059f4")
        AdSettings.addTestDevice("39f6d89e-52bf-431f-a14f-a10089fb4133")
        AdSettings.addTestDevice("1af51df7-5a30-424b-9b37-3e996ad7adeb")
        AdSettings.addTestDevice("52b2b83c-a4a4-4982-bf25-14b8b787297d")
        AdSettings.addTestDevice("2e16c27e-34cf-432e-bfaf-18654b3791d3")
        AdSettings.addTestDevice("0d016b06-da99-44a4-8362-e0d0b6ff733d");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        currentActivity?.let {
            if (!isAdsClicking && !isAdsShowing){
                AdsManage.ActivityBuilder().Show_AppOpenAd(it,false,Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT,object : OnAppOpenShowAds {
                    override fun OnDismissAds() {
                        loadAppOpenAd(false, AD_AppOpen!!, Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT)
                    }

                    override fun OnError() {

                    }
                })
            }else{
                isAdsClicking=false
            }

        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}