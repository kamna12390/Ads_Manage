package com.demo.adsmanage.AdsClass
import android.app.Activity
import android.content.Context
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isAdsShowing
import com.demo.adsmanage.Commen.Constants.isInter_RequestSend
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.isTestMode
import com.demo.adsmanage.Commen.Constants.mInterstitialAdlist
import com.demo.adsmanage.InterFace.OnInterstitialAds
import com.demo.adsmanage.helper.MySharedPreferences.AD_Interstitial
import com.demo.adsmanage.helper.MySharedPreferences.FB_Interstitial
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.facebook.ads.Ad
import com.facebook.ads.InterstitialAdListener
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object InterstitialAds {
    val TAG = this.javaClass.simpleName

    internal  fun Context.loadInterstitialAd(
    ) {

            var id = if (isTestMode!!) {
                "ca-app-pub-3940256099942544/1033173712"
            } else {
                AD_Interstitial
            }
            logD(TAG, "ADSMANAGE  InterstitialID Admob->$id")
            isInter_RequestSend = true
            InterstitialAd.load(
                this,
                id!!,
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        isInter_RequestSend = false
                        mInterstitialAdlist=null
                        logD(
                            TAG,
                            "ADSMANAGE: onAdFailedToLoad:interstitialAd->AdMob ->${adError.message}"
                        )
                        if (isShowAdmobAds){
                            loadFBInterstitialSd()
                        }
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        isInter_RequestSend = false
                        mInterstitialAdlist=interstitialAd
                        logD(TAG, "ADSMANAGE: onAdLoaded:interstitialAd->AdMob")

                    }
                })


    }

    internal   fun Context.loadFBInterstitialSd() {

        if (mInterstitialAdlist == null) {
            isInter_RequestSend=true
            var id = if (isTestMode!!) {
                "YOUR_PLACEMENT_ID"
            }
            else {
                FB_Interstitial
            }
            logD(TAG, "ADSMANAGE  InterstitialID Facebook->$id")
            mInterstitialAdlist=com.facebook.ads.InterstitialAd(this, id)
            val interstitialAdListener = object : InterstitialAdListener {
                override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                    isInter_RequestSend=false
                    mInterstitialAdlist=null
                    if (!isShowAdmobAds){
                        loadInterstitialAd()
                    }
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:interstitialAd->Facebook ->${p1!!.errorMessage}"
                    )
                }

                override fun onAdLoaded(p0: Ad?) {
                    isInter_RequestSend=false
                    logD(TAG, "ADSMANAGE: onAdLoaded:interstitialAd->Facebook ")
                }

                override fun onAdClicked(p0: Ad?) {
                    isAdsClicking =true
                    isInter_RequestSend=false

                }

                override fun onLoggingImpression(p0: Ad?) {

                }

                override fun onInterstitialDisplayed(p0: Ad?) {

                }

                override fun onInterstitialDismissed(p0: Ad?) {
                    isInter_RequestSend=false
                    mInterstitialAdlist=null

                    logD(TAG, "ADSMANAGE: onAdDismissed:interstitialAd->Facebook ")
                    if (isShowAdmobAds){
                        loadInterstitialAd()
                    }else{
                        loadFBInterstitialSd()
                    }

                }

            }
            (mInterstitialAdlist as com.facebook.ads.InterstitialAd).loadAd(
                (mInterstitialAdlist as com.facebook.ads.InterstitialAd).buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
            )
        }
    }

    internal  fun Context.showInterstitialAd(
        is_SUBSCRIBED: Boolean? = false,
        onInterstitialAds: OnInterstitialAds
    ) {
        if (is_SUBSCRIBED!! && isOnline) {
            onInterstitialAds.OnDismissAds()
        } else {
            if ( mInterstitialAdlist != null && mInterstitialAdlist is InterstitialAd ) {
                (mInterstitialAdlist as InterstitialAd).fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            super.onAdClicked()
                            isAdsClicking =true
                        }
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            isAdsShowing = false
                            mInterstitialAdlist = null
                            logD(
                                TAG,
                                "ADSMANAGE: onAdDismissedFullScreenContent:interstitialAd->AdMob "
                            )
                            if (isShowAdmobAds){
                                loadInterstitialAd()
                            }else{
                                loadFBInterstitialSd()
                            }

                            onInterstitialAds.OnDismissAds()

                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            isAdsShowing = false
                            mInterstitialAdlist=null

                            logD(
                                TAG,
                                "ADSMANAGE: onAdFailedToShowFullScreenContent:interstitialAd->AdMob->${p0.message} "
                            )
                            onInterstitialAds.OnError()
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            isAdsShowing = true
                        }
                    }
                (mInterstitialAdlist as InterstitialAd).show(this as Activity)
            }
            else if (mInterstitialAdlist!=null && (mInterstitialAdlist as com.facebook.ads.InterstitialAd).isAdLoaded){
                logD(
                    TAG,
                    "ADSMANAGE: OnAdShow:interstitialAd->Facebook "
                )
                val interstitialAdListener = object : InterstitialAdListener {
                    override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                        mInterstitialAdlist=null
                        onInterstitialAds.OnError()
                        if (!isShowAdmobAds){
                            loadInterstitialAd()
                        }
                        logD(
                            TAG,
                            "ADSMANAGE: tttonAdFailedToLoad:interstitialAd->Facebook ->${p1!!.errorMessage}"
                        )
                    }

                    override fun onAdLoaded(p0: Ad?) {
                        logD(TAG, "ADSMANAGE: ttonAdLoaded:interstitialAd->Facebook ")
                    }

                    override fun onAdClicked(p0: Ad?) {
                        isAdsClicking =true
                        isInter_RequestSend=false

                    }

                    override fun onLoggingImpression(p0: Ad?) {

                    }

                    override fun onInterstitialDisplayed(p0: Ad?) {

                    }

                    override fun onInterstitialDismissed(p0: Ad?) {
                        isInter_RequestSend=false
                        mInterstitialAdlist=null

                        logD(TAG, "ADSMANAGE: onAdDismissed:interstitialAd->Facebook ")
                        if (isShowAdmobAds){
                            loadInterstitialAd()
                        }else{
                            loadFBInterstitialSd()
                        }
                        onInterstitialAds.OnDismissAds()

                    }

                }
                (mInterstitialAdlist as com.facebook.ads.InterstitialAd).buildLoadAdConfig().withAdListener(interstitialAdListener)
                (mInterstitialAdlist as com.facebook.ads.InterstitialAd).show()
            } else {
                logD(TAG, "ADSMANAGE: Ads Not Loaded:interstitialAd->AdMob ")
                mInterstitialAdlist=null
                isAdsShowing = false
                if (isShowAdmobAds){
                    loadInterstitialAd()
                }else{
                    loadFBInterstitialSd()
                }

                onInterstitialAds.OnDismissAds()
            }


        }


    }
}


