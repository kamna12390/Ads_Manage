package com.demo.myadsmanage.InterFace

import com.google.android.gms.ads.interstitial.InterstitialAd

interface OnRewardedShowAds {
    fun OnDismissAds( )
    fun OnUserEarned( )
    fun OnError( )
}