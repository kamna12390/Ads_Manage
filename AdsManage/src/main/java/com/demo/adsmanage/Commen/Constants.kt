package com.demo.adsmanage.Commen
import android.content.SharedPreferences

object Constants {
    var mInterstitialAdlist:Any?=null
    var mNativeAdlist:Any?=null
    var mRewardedAds:Any?=null
    var mAppOpenAds:Any?=null
    var mAppOpenAds_LANDSCAPE:Any?=null

    var isInter_RequestSend: Boolean=false
    var isRewarde_RequestSend: Boolean=false
    var isAppOpen_RequestSend: Boolean=false
    var isAppOpen_RequestSend_LANDSCAPE: Boolean=false
    var isAdsShowing: Boolean=false
    var isTestMode: Boolean=false
    var isDebugMode: Boolean=true
    var isAdsClicking: Boolean=true
    var isShowAdmobAds: Boolean=true
    var isHomeNativeShow: Boolean=true
    var isSettingNativeShow: Boolean=true
    var isCreationNativeShow: Boolean=true
    var is_ProgressShow: Boolean=false
    var is_BackAdsShow: Boolean=false
    var is_ABTest: Int=0

    var mPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
     val APP_OPEN_AD_ORIENTATION_PORTRAIT = 1
     val APP_OPEN_AD_ORIENTATION_LANDSCAPE = 2
    var mInterstitialAds_clickCount=0
    var mInterAdsRequest_pos=0

    var Noads="NoAds"

}