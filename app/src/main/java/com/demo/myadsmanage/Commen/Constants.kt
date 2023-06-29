package com.demo.myadsmanage.Commen

import com.google.android.gms.ads.rewarded.RewardedAd

object Constants {
    var mInterstitialAdlist= arrayListOf<Any?>(null,null,null,null,null)
    var mNativeAdlist= arrayListOf<Any?>(null,null)
    var mRewardedAds:Any?=null
    var mAppOpenAds:Any?=null

    var isInter_RequestSend: Boolean=false
    var isRewarde_RequestSend: Boolean=false
    var isAppOpen_RequestSend: Boolean=false
    var isAdsShowing: Boolean=false
    var isTestMode: Boolean=false
    var isDebugMode: Boolean=true
    var isAdsClicking: Boolean=true
    var isShowAdmobAds: Boolean=true
    var isHomeNativeShow: Boolean=true
    var isSettingNativeShow: Boolean=true
    var isCreationNativeShow: Boolean=true
    var is_ProgressShow: Boolean=true


    var mInterstitialAds_clickCount=0
    var mInterAdsRequest_pos=0

}