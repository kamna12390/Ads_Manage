package com.demo.adsmanage.InterFace

import com.google.android.gms.ads.interstitial.InterstitialAd

interface OnInterstitialLoadAds {
    fun OnDismissAds( inter_pos:Int,mInterstitialAd: InterstitialAd?)
    fun OnError( inter_pos:Int,mInterstitialAd: InterstitialAd?)
}