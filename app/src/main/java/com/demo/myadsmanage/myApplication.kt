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
import com.demo.myadsmanage.AdsClass.AppOpenAds.loadAppOpenAd
import com.demo.myadsmanage.Commen.Constants.isAdsClicking
import com.demo.myadsmanage.Commen.Constants.isAdsShowing
import com.demo.myadsmanage.InterFace.OnAppOpenShowAds
import com.demo.myadsmanage.helper.MySharedPreferences
import com.demo.myadsmanage.helper.MySharedPreferences.AD_AppOpen
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.appopen.AppOpenAd
import java.util.Arrays

class myApplication : Application(), LifecycleObserver,
    Application.ActivityLifecycleCallbacks {
    companion object {
        var mPreferences: SharedPreferences? = null
        var editor: SharedPreferences.Editor? = null
    }

    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        AudienceNetworkAds.initialize(this);
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        mPreferences = applicationContext.getSharedPreferences("MyAdsClass", Context.MODE_PRIVATE)
        editor = mPreferences!!.edit()
        RequestConfiguration.Builder().setTestDeviceIds(listOf("B8A9DD98F4E273AF23DA6ADBFFCE55E9"))
        AdSettings.addTestDevice("369fac27-5a40-4d91-9e86-bc0a7d221977")
        AdSettings.addTestDevice("c12a6da6-0672-4112-97c6-078dc7774b7b")
        AdSettings.addTestDevice("2a2fe541-44d6-40b2-8db8-6b6c30bb1946")
        AdSettings.addTestDevice("e09c375f-c62a-4cfe-a545-abb4fbfd8e3b");
        AdSettings.addTestDevice("c3a7427f-18eb-4349-bffd-d3c8e46059f4");
        AdSettings.addTestDevice("39f6d89e-52bf-431f-a14f-a10089fb4133")
        AdSettings.addTestDevice("1af51df7-5a30-424b-9b37-3e996ad7adeb");
        AdSettings.addTestDevice("52b2b83c-a4a4-4982-bf25-14b8b787297d");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        currentActivity?.let {
            if (!isAdsClicking && !isAdsShowing){
                AdsManage.ActivityBuilder().Show_AppOpenAd(it,false,object : OnAppOpenShowAds {
                    override fun OnDismissAds() {
                        loadAppOpenAd(false, AD_AppOpen!!,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT)
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