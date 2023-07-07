package com.demo.adsmanage.AdsClass
import android.app.Activity
import android.content.Context
import com.demo.adsmanage.Commen.Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT
import com.demo.adsmanage.Commen.Constants.isAppOpen_RequestSend
import com.demo.adsmanage.Commen.Constants.isAppOpen_RequestSend_LANDSCAPE
import com.demo.adsmanage.Commen.Constants.mAppOpenAds
import com.demo.adsmanage.Commen.Constants.mAppOpenAds_LANDSCAPE
import com.demo.adsmanage.InterFace.OnAppOpenShowAds
import com.demo.adsmanage.InterFace.OnSplachAds
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

object AppOpenAds {
    val TAG = this.javaClass.simpleName
    fun Context.loadAppOpenAd(  is_SUBSCRIBED: Boolean,
                                mAD_AppOpenID: String,appOpenAd: Int){
        if (mAD_AppOpenID==null){
            return
        }
        if (appOpenAd==APP_OPEN_AD_ORIENTATION_PORTRAIT){
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
        }else{
            if (mAppOpenAds_LANDSCAPE!=null && isAppOpen_RequestSend_LANDSCAPE && !isOnline && is_SUBSCRIBED){
                return
            }
            isAppOpen_RequestSend_LANDSCAPE=true
            logD(TAG, "ADSMANAGE  AppOpenID Admob->$mAD_AppOpenID")
            AppOpenAd.load(this,mAD_AppOpenID,AdRequest.Builder().build(),appOpenAd,object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(p0: AppOpenAd) {
                    isAppOpen_RequestSend_LANDSCAPE=false
                    mAppOpenAds_LANDSCAPE=p0
                    logD(
                        TAG,
                        "ADSMANAGE: onAdLoaded:AppOpenAds->AdMob"
                    )
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    isAppOpen_RequestSend_LANDSCAPE=false
                    mAppOpenAds_LANDSCAPE=null
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:AppOpenAds->AdMob ->${p0.message}"
                    )
                }
            })
        }

    }
    fun Context.loadFirsttimeAppOpenAd(  is_SUBSCRIBED: Boolean,
                                mAD_AppOpenID: String,appOpenAd: Int,onSplachAds: OnSplachAds
    ){
        if (mAD_AppOpenID==null){
            return
        }
        if (appOpenAd==APP_OPEN_AD_ORIENTATION_PORTRAIT){
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
                    onSplachAds.OnNextAds()
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    isAppOpen_RequestSend=false
                    mAppOpenAds=null
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:AppOpenAds->AdMob ->${p0.message}"
                    )
                    onSplachAds.OnNextAds()
                }
            })
        }else{
            if (mAppOpenAds_LANDSCAPE!=null && isAppOpen_RequestSend_LANDSCAPE && !isOnline && is_SUBSCRIBED){
                return
            }
            isAppOpen_RequestSend_LANDSCAPE=true
            logD(TAG, "ADSMANAGE  AppOpenID Admob->$mAD_AppOpenID")
            AppOpenAd.load(this,mAD_AppOpenID,AdRequest.Builder().build(),appOpenAd,object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(p0: AppOpenAd) {
                    isAppOpen_RequestSend_LANDSCAPE=false
                    mAppOpenAds_LANDSCAPE=p0
                    logD(
                        TAG,
                        "ADSMANAGE: onAdLoaded:AppOpenAds->AdMob"
                    )
                    onSplachAds.OnNextAds()
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    isAppOpen_RequestSend_LANDSCAPE=false
                    mAppOpenAds_LANDSCAPE=null
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:AppOpenAds->AdMob ->${p0.message}"
                    )
                    onSplachAds.OnNextAds()
                }
            })
        }

    }


    fun Context.showAppOpenAd(is_SUBSCRIBED: Boolean? = false,appOpenAd: Int,onAppOpenShowAds: OnAppOpenShowAds){
        if (appOpenAd==APP_OPEN_AD_ORIENTATION_PORTRAIT){
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
        }else if (mAppOpenAds_LANDSCAPE is AppOpenAd){
            if (mAppOpenAds_LANDSCAPE!=null && mAppOpenAds_LANDSCAPE is AppOpenAd){
                (mAppOpenAds_LANDSCAPE as AppOpenAd).fullScreenContentCallback=object :FullScreenContentCallback(){
                    override fun onAdDismissedFullScreenContent() {
                        mAppOpenAds_LANDSCAPE=null
                        onAppOpenShowAds.OnDismissAds()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        mAppOpenAds_LANDSCAPE=null
                        onAppOpenShowAds.OnError()
                    }

                    override fun onAdShowedFullScreenContent() {

                    }
                }
                (mAppOpenAds_LANDSCAPE as AppOpenAd).show(this as Activity)
            }
        }else{
            onAppOpenShowAds.OnDismissAds()
        }

    }
}