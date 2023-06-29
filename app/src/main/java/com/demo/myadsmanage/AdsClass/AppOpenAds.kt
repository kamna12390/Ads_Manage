package com.demo.myadsmanage.AdsClass

import android.app.Activity
import android.content.Context
import com.demo.myadsmanage.Commen.Constants.isAppOpen_RequestSend
import com.demo.myadsmanage.Commen.Constants.mAppOpenAds
import com.demo.myadsmanage.InterFace.OnAppOpenShowAds
import com.demo.myadsmanage.helper.MySharedPreferences.AD_AppOpen
import com.demo.myadsmanage.helper.isOnline
import com.demo.myadsmanage.helper.logD
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

object AppOpenAds {
    val TAG = this.javaClass.simpleName
    fun Context.loadAppOpenAd(  is_SUBSCRIBED: Boolean,
                                mAD_AppOpenID: String,appOpenAd: Int){
        if (mAppOpenAds!=null && isAppOpen_RequestSend && !isOnline && is_SUBSCRIBED){
            return
        }
        isAppOpen_RequestSend=true
        logD(TAG, "ADSMANAGE  AppOpenID Admob->$mAD_AppOpenID")
        AppOpenAd.load(this,mAD_AppOpenID,AdRequest.Builder().build(),appOpenAd,object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(p0: AppOpenAd) {
                isAppOpen_RequestSend=false
                mAppOpenAds=p0
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AppOpenAds->AdMob"
                )
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                isAppOpen_RequestSend=false
                mAppOpenAds=null
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AppOpenAds->AdMob ->${p0.message}"
                )
            }
        })
    }


    fun Context.showAppOpenAd(is_SUBSCRIBED: Boolean? = false,onAppOpenShowAds: OnAppOpenShowAds){
        if (mAppOpenAds!=null && mAppOpenAds is AppOpenAd){
            (mAppOpenAds as AppOpenAd).fullScreenContentCallback=object :FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    mAppOpenAds=null
                    onAppOpenShowAds.OnDismissAds()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    mAppOpenAds=null
                    onAppOpenShowAds.OnError()
                }

                override fun onAdShowedFullScreenContent() {

                }
            }
            (mAppOpenAds as AppOpenAd).show(this as Activity)
        }
    }
}