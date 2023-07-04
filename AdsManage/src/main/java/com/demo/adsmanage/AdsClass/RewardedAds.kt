package com.demo.adsmanage.AdsClass
import android.app.Activity
import android.content.Context
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isAdsShowing
import com.demo.adsmanage.Commen.Constants.isRewarde_RequestSend
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.mRewardedAds
import com.demo.adsmanage.InterFace.OnRewardedShowAds
import com.demo.adsmanage.helper.MySharedPreferences.AD_RewardedAds
import com.demo.adsmanage.helper.MySharedPreferences.FB_RewardedAds
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.facebook.ads.Ad
import com.facebook.ads.RewardedVideoAd
import com.facebook.ads.RewardedVideoAdListener
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback


object RewardedAds {
    val TAG = this.javaClass.simpleName
    fun Context.loadRewardedAds(
        is_SUBSCRIBED: Boolean,
        mAD_RewardID: String
    ) {
        if (isRewarde_RequestSend && mRewardedAds!=null && !isOnline && is_SUBSCRIBED && mAD_RewardID==null){
            return
        }
        logD(TAG, "ADSMANAGE  RewardedID Admob->$mAD_RewardID")
        isRewarde_RequestSend=true
        RewardedAd.load(this,mAD_RewardID,AdRequest.Builder().build(),object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                isRewarde_RequestSend=false
                mRewardedAds=null
                if (isShowAdmobAds){
                    loadFBRewatdedAD(is_SUBSCRIBED, FB_RewardedAds!!)
                }
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:RewardedAds->AdMob ->${p0.message}"
                )
            }

            override fun onAdLoaded(mrewardedAd: RewardedAd) {
                isRewarde_RequestSend=false
                mRewardedAds=mrewardedAd
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:RewardedAds->AdMob"
                )
            }
        })
    }

    fun Context.loadFBRewatdedAD(is_SUBSCRIBED: Boolean,
                                 mAD_RewardID: String){
        if (mAD_RewardID==null){
            return
        }
        logD(TAG, "ADSMANAGE  RewardedID Facebook->$mAD_RewardID")
        isRewarde_RequestSend=true
        mRewardedAds = RewardedVideoAd(this, mAD_RewardID)
        val rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                isRewarde_RequestSend=false
                mRewardedAds=null
                if (!isShowAdmobAds){
                    loadRewardedAds(is_SUBSCRIBED, AD_RewardedAds!!)
                }
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:RewardedAds->Facebook ->${p1!!.errorMessage}"
                )
            }

            override fun onAdLoaded(p0: Ad?) {
                isRewarde_RequestSend=false
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:RewardedAds->Facebook"
                )
            }
            override fun onAdClicked(p0: Ad?) {
                isAdsClicking =true
            }

            override fun onLoggingImpression(p0: Ad?) {

            }

            override fun onRewardedVideoCompleted() {

            }

            override fun onRewardedVideoClosed() {
                isAdsShowing=false
                isRewarde_RequestSend=false
                mRewardedAds=null
                if (isShowAdmobAds){
                    loadRewardedAds(is_SUBSCRIBED, AD_RewardedAds!!)
                }else{
                    loadFBRewatdedAD(is_SUBSCRIBED, FB_RewardedAds!!)
                }

                logD(
                    TAG,
                    "ADSMANAGE: onRewardedVideoClosed:RewardedAds->Facebook"
                )
            }

        }
        (mRewardedAds as RewardedVideoAd).loadAd(
            (mRewardedAds as RewardedVideoAd).buildLoadAdConfig()
                .withAdListener(rewardedVideoAdListener)
                .build()
        )
    }
    fun Context.showRewarded(   is_SUBSCRIBED: Boolean? = false,onRewardedShowAds: OnRewardedShowAds){
        if (mRewardedAds!=null && mRewardedAds is RewardedAd){
            (mRewardedAds as RewardedAd).fullScreenContentCallback=object :FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    isAdsShowing=false
                    mRewardedAds=null
                    if (isShowAdmobAds){
                        loadRewardedAds(is_SUBSCRIBED!!, AD_RewardedAds!!)
                    }else{
                        loadFBRewatdedAD(is_SUBSCRIBED!!, FB_RewardedAds!!)
                    }
                    onRewardedShowAds.OnDismissAds()
                    logD(
                        TAG,
                        "ADSMANAGE: onAdDismissedFullScreenContent:RewardedAds->AdMob "
                    )
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    isAdsShowing=true
                }
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToShowFullScreenContent:RewardedAds->AdMob->${p0.message} "
                    )
                    onRewardedShowAds.OnError()
                }

                override fun onAdClicked() {
                    super.onAdClicked()
                    isAdsClicking=true
                }
            }
            (mRewardedAds as RewardedAd).show(this as Activity, OnUserEarnedRewardListener {
                logD(
                    TAG,
                    "ADSMANAGE: OnUserEarnedRewardListener:RewardedAds->AdMob "
                )
                onRewardedShowAds.OnUserEarned()

            })
        }else if (mRewardedAds!=null && mRewardedAds is RewardedVideoAd){
            isAdsShowing=true
            logD(
                TAG,
                "ADSMANAGE: Showing:RewardedAds->Facebook"
            )
            val rewardedVideoAdListener = object : RewardedVideoAdListener {
                override fun onError(p0: Ad?, p1: com.facebook.ads.AdError?) {
                    isRewarde_RequestSend=false
                    mRewardedAds=null
                    if (!isShowAdmobAds){
                        loadRewardedAds(is_SUBSCRIBED!!, AD_RewardedAds!!)
                    }
                    logD(
                        TAG,
                        "ADSMANAGE: onAdFailedToLoad:RewardedAds->Facebook ->${p1!!.errorMessage}"
                    )
                }

                override fun onAdLoaded(p0: Ad?) {
                    isRewarde_RequestSend=false
                    logD(
                        TAG,
                        "ADSMANAGE: onAdLoaded:RewardedAds->Facebook"
                    )
                }
                override fun onAdClicked(p0: Ad?) {
                    isAdsClicking =true
                }

                override fun onLoggingImpression(p0: Ad?) {

                }

                override fun onRewardedVideoCompleted() {

                }

                override fun onRewardedVideoClosed() {
                    isAdsShowing=false
                    isRewarde_RequestSend=false
                    mRewardedAds=null
                    if (isShowAdmobAds){
                        loadRewardedAds(is_SUBSCRIBED!!, AD_RewardedAds!!)
                    }else{
                        loadFBRewatdedAD(is_SUBSCRIBED!!, FB_RewardedAds!!)
                    }

                    logD(
                        TAG,
                        "ADSMANAGE: onRewardedVideoClosed:RewardedAds->Facebook"
                    )
                    onRewardedShowAds.OnUserEarned()
                }

            }
            (mRewardedAds as RewardedVideoAd).buildLoadAdConfig().withAdListener(rewardedVideoAdListener)
            (mRewardedAds as RewardedVideoAd).show()
        }else{
            logD(
                TAG,
                "ADSMANAGE: Ads Not Load:RewardedAds->AdMob"
            )
            if (isShowAdmobAds){
                loadRewardedAds(is_SUBSCRIBED!!, AD_RewardedAds!!)
            }else{
                loadFBRewatdedAD(is_SUBSCRIBED!!, FB_RewardedAds!!)
            }
            onRewardedShowAds.OnError()
        }

    }
}