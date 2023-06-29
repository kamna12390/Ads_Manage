package com.demo.adsmanage.AdsClass
import android.app.Activity
import android.content.Context
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isAdsShowing
import com.demo.adsmanage.Commen.Constants.isInter_RequestSend
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.isTestMode
import com.demo.adsmanage.Commen.Constants.mInterAdsRequest_pos
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

    fun Context.loadInterstitialAd(
        is_SUBSCRIBED: Boolean,
        mAD_Interstitial: String,
        inter_pos:Int
    ) {
        if ( !isOnline && is_SUBSCRIBED) {
            return
        }

            var id = if (isTestMode!!) {
                "ca-app-pub-3940256099942544/1033173712"
            } else {
                mAD_Interstitial
            }
            logD(TAG, "ADSMANAGE  InterstitialID Admob->$id->$inter_pos")
            isInter_RequestSend = true
            InterstitialAd.load(
                this,
                id!!,
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        isInter_RequestSend = false
                        mInterstitialAdlist[inter_pos]=null
//                        val mid= AD_Interstitial[mInterAdsRequest_pos]
                        logD(
                            TAG,
                            "ADSMANAGE: onAdFailedToLoad:interstitialAd->AdMob ->${adError.message}"
                        )
//                        if(mAD_Interstitial.equals(mid,true) && AD_Interstitial.size>(mInterAdsRequest_pos+1)){
//                            mInterAdsRequest_pos++
//                            loadInterstitialAd(
//                                is_SUBSCRIBED,
//                                AD_Interstitial[mInterAdsRequest_pos]
//                            )
//
//                        }else{
                        if (isShowAdmobAds){
                            loadFBInterstitialSd(is_SUBSCRIBED,inter_pos)
                        }

//                        }


                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        isInter_RequestSend = false
                        mInterstitialAdlist.set(inter_pos,interstitialAd)
                        mInterAdsRequest_pos=0
                        logD(TAG, "ADSMANAGE: onAdLoaded:interstitialAd->AdMob--inter_pos=>$inter_pos---mInterstitialAdlist->${mInterstitialAdlist.size} ")

                    }
                })


    }

    fun Context.loadFBInterstitialSd(is_SUBSCRIBED: Boolean,inter_pos:Int) {
        if ( !isOnline && is_SUBSCRIBED) {
            return
        }
        if (mInterstitialAdlist[inter_pos] == null) {
            isInter_RequestSend=true
            var id = if (isTestMode!!) {
                "YOUR_PLACEMENT_ID"
            }
            else {
                FB_Interstitial
            }
            logD(TAG, "ADSMANAGE  InterstitialID Facebook->$id")
            mInterstitialAdlist[inter_pos]=com.facebook.ads.InterstitialAd(this, id)
            val interstitialAdListener = object : InterstitialAdListener {
                override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                    isInter_RequestSend=false
                    mInterstitialAdlist[inter_pos]=null
                    if (!isShowAdmobAds){
                        loadInterstitialAd(is_SUBSCRIBED, AD_Interstitial[inter_pos],inter_pos)
                    }
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:interstitialAd->Facebook ->${p1!!.errorMessage}"
                    )
                }

                override fun onAdLoaded(p0: Ad?) {
                    isInter_RequestSend=false
                    mInterAdsRequest_pos=0
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
                    mInterstitialAdlist[inter_pos]=null
                    logD(TAG, "ADSMANAGE: onAdDismissed:interstitialAd->Facebook ")
                    if (isShowAdmobAds){
                        loadInterstitialAd(
                            is_SUBSCRIBED,
                            AD_Interstitial[inter_pos],
                            inter_pos
                        )
                    }else{
                        loadFBInterstitialSd(is_SUBSCRIBED,inter_pos)
                    }

                }

            }
            (mInterstitialAdlist[inter_pos] as com.facebook.ads.InterstitialAd).loadAd(
                (mInterstitialAdlist[inter_pos] as com.facebook.ads.InterstitialAd).buildLoadAdConfig()
                    .withAdListener(interstitialAdListener)
                    .build()
            )
        }
    }

    fun Context.showInterstitialAd(
        is_SUBSCRIBED: Boolean? = false,
        inter_pos:Int,
        onInterstitialAds: OnInterstitialAds
    ) {
        if (is_SUBSCRIBED!!) {
            onInterstitialAds.OnDismissAds()
        } else {
            if ( mInterstitialAdlist[inter_pos] != null && mInterstitialAdlist[inter_pos] is InterstitialAd ) {
                (mInterstitialAdlist[inter_pos] as InterstitialAd).fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            super.onAdClicked()
                            isAdsClicking =true
                        }
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            isAdsShowing = false
                            mInterstitialAdlist[inter_pos] = null
                            logD(
                                TAG,
                                "ADSMANAGE: onAdDismissedFullScreenContent:interstitialAd->AdMob "
                            )
                            if (isShowAdmobAds){
                                loadInterstitialAd(
                                    is_SUBSCRIBED,
                                    AD_Interstitial[inter_pos],
                                    inter_pos
                                )
                            }else{
                                loadFBInterstitialSd(is_SUBSCRIBED,inter_pos)
                            }

                            onInterstitialAds.OnDismissAds()

                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            isAdsShowing = false
//                            mInterstitialAd = null
                            mInterstitialAdlist[inter_pos]=null

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
                (mInterstitialAdlist[inter_pos] as InterstitialAd).show(this as Activity)
            }
            else if (mInterstitialAdlist[inter_pos]!=null && (mInterstitialAdlist[inter_pos] as com.facebook.ads.InterstitialAd).isAdLoaded){
                logD(
                    TAG,
                    "ADSMANAGE: OnAdShow:interstitialAd->Facebook "
                )
                (mInterstitialAdlist[inter_pos] as com.facebook.ads.InterstitialAd).show()
            } else {
                logD(TAG, "ADSMANAGE: Ads Not Loaded:interstitialAd->AdMob ")
                mInterstitialAdlist.add(inter_pos,null)
                isAdsShowing = false
                if (isShowAdmobAds){
                    loadInterstitialAd(is_SUBSCRIBED, AD_Interstitial[inter_pos],inter_pos)
                }else{
                    loadFBInterstitialSd(is_SUBSCRIBED,inter_pos)
                }

                onInterstitialAds.OnDismissAds()
            }


        }


    }
}


