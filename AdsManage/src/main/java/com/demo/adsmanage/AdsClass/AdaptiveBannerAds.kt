package com.demo.adsmanage.AdsClass

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.InterFace.IsShowBannerAds
import com.demo.adsmanage.InterFace.OnCustomBanner
import com.demo.adsmanage.helper.MySharedPreferences.AD_Banner
import com.demo.adsmanage.helper.MySharedPreferences.FB_Banner
import com.demo.adsmanage.helper.logD
import com.demo.adsmanage.helper.pxToDp
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError


object AdaptiveBannerAds {
    val TAG = this.javaClass.simpleName
    @SuppressLint("NewApi")
    internal   fun Context.loadAdaptiveBanner(
        view: ViewGroup
    ) {

        var id = if (Constants.isTestMode) {
            "ca-app-pub-3940256099942544/6300978111"
        } else{
            AD_Banner
        }
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->${id}")
        val madView = AdView(this)
        madView.adUnitId = id!!
        madView.setAdSize(getAdSize(view))
        madView.loadAd(AdRequest.Builder().build())
        madView.adListener = object : AdListener() {
            override fun onAdClicked() {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Admob"
                )
            }

            override fun onAdClosed() {
                logD(
                    TAG,
                    "ADSMANAGE: onAdClosed:AdaptiveBannerAds->Admob"
                )
            }

            override fun onAdLoaded() {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Admob"
                )
                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                view.removeAllViews()
                view.addView(madView,params)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Admob--${p0.message}"
                )
//                if (isShowAdmobAds){
//                    loadFBAdaptiveBanner(is_SUBSCRIBED, view, FB_Banner!!)
//                }
            }
        }

    }
    internal   fun Context.loadAdaptiveBannerSize(
        view: ViewGroup,isShowBannerAds: IsShowBannerAds,onCustomBanner: OnCustomBanner) {


        var id = if (Constants.isTestMode) {
            "ca-app-pub-3940256099942544/6300978111"
        } else{
            AD_Banner
//            "ca-app-pub-7441144866662686/3939039229"
        }
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->${id}")
        val massize=if (isShowBannerAds==IsShowBannerAds.MEDIUM_RECTANGLE){
            AdSize.MEDIUM_RECTANGLE
        }else if (isShowBannerAds==IsShowBannerAds.LEADERBOARD){
            AdSize.LEADERBOARD
        }else if (isShowBannerAds==IsShowBannerAds.SMART_BANNER){
            AdSize.SMART_BANNER
        }else if (isShowBannerAds==IsShowBannerAds.FULL_BANNER){
            AdSize.FULL_BANNER
        }else{
            AdSize.BANNER
        }
        val madView = AdView(this)
        madView.adUnitId = id!!
        madView.setAdSize(massize)
        madView.loadAd(AdRequest.Builder().build())
        madView.adListener = object : AdListener() {
            override fun onAdClicked() {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Admob"
                )
                onCustomBanner.OnBannerAdsClick()
            }

            override fun onAdClosed() {
                logD(
                    TAG,
                    "ADSMANAGE: onAdClosed:AdaptiveBannerAds->Admob"
                )
            }

            override fun onAdLoaded() {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Admob"
                )
                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                view.removeAllViews()
                view.addView(madView,params)
                onCustomBanner.OnBannerAdsShow()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Admob--${p0.message}"
                )
                onCustomBanner.OnBannerAdsError()
            }
        }

    }

    internal fun Context.loadAdaptiveBannerCustomSize(view: ViewGroup, maxWidth:Int?=0, maxHeight:Int?=0,onCustomBanner: OnCustomBanner) {
        var screenWidth=maxWidth
        var screenHeight=maxHeight
        if (screenWidth==0){
            screenWidth = pxToDp(resources.displayMetrics.widthPixels)
            screenWidth-=5
        }
        if (screenHeight==0){
            screenHeight =pxToDp(resources.displayMetrics.heightPixels)
            screenHeight = screenHeight.div(2)
        }

        val bannerWidth = screenWidth
        val bannerHeight =  screenHeight

        var id = if (Constants.isTestMode) {
            "ca-app-pub-3940256099942544/6300978111"
        } else{
            AD_Banner
        }
        Log.d(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->Id-${id}-bannerWidth-${bannerWidth}-bannerHeight-${bannerHeight}")
        val madView = AdView(this)
        madView.adUnitId = id!!

        val adSize = AdSize(bannerWidth!!, (bannerHeight!!-50))
        madView.setAdSize(adSize)
        madView.loadAd(AdRequest.Builder().build())
        madView.adListener = object : AdListener() {
            override fun onAdClicked() {
                isAdsClicking =true
                Log.d(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Admob"
                )
                onCustomBanner.OnBannerAdsClick()
            }

            override fun onAdClosed() {
                Log.d(
                    TAG,
                    "ADSMANAGE: onAdClosed:AdaptiveBannerAds->Admob"
                )
            }

            override fun onAdLoaded() {
                Log.d(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Admob"
                )
                val params: LinearLayout.LayoutParams =
                    LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                view.removeAllViews()
                view.addView(madView,params)
                onCustomBanner.OnBannerAdsShow()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Admob--${p0.message}"
                )
                onCustomBanner.OnBannerAdsError()
            }
        }

    }
    internal fun Context.loadFBAdaptiveBanner(
        view: ViewGroup
    ) {
        var id = if (Constants.isTestMode!!) {
            "YOUR_PLACEMENT_ID"
        } else{
            FB_Banner
        }
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds Facebook->$id---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.height}---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.width}")
        val madView = com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
        view.addView(madView)
        val adListener: com.facebook.ads.AdListener = object : com.facebook.ads.AdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
//                if (!isShowAdmobAds){
//                    loadAdaptiveBanner(is_SUBSCRIBED,view, MySharedPreferences.AD_Banner!!)
//                }
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Facebook"
                )
            }

            override fun onAdLoaded(p0: Ad?) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Facebook"
                )
            }

            override fun onAdClicked(p0: Ad?) {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Facebook"
                )
            }

            override fun onLoggingImpression(p0: Ad?) {

            }


        }
        val loadAdConfig = madView.buildLoadAdConfig().withAdListener(adListener).build()
        madView.loadAd(loadAdConfig)
    }

    internal fun Context.loadFBAdaptiveBannerSize(
        view: ViewGroup,isShowBannerAds: IsShowBannerAds,onCustomBanner: OnCustomBanner) {
        var id = if (Constants.isTestMode!!) {
            "YOUR_PLACEMENT_ID"
        } else{
            FB_Banner
        }
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds Facebook->$id---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.height}---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.width}")
//        val madView = com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
        val madView=if (isShowBannerAds==IsShowBannerAds.MEDIUM_RECTANGLE){
            com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250)
        }else if (isShowBannerAds==IsShowBannerAds.LEADERBOARD){
            com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_90)
        }else if (isShowBannerAds==IsShowBannerAds.SMART_BANNER){
            com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_90)
        }else if (isShowBannerAds==IsShowBannerAds.FULL_BANNER){
            com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
        }else{
            com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
        }
        view.addView(madView)
        val adListener: com.facebook.ads.AdListener = object : com.facebook.ads.AdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
//                if (!isShowAdmobAds){
//                    loadAdaptiveBanner(is_SUBSCRIBED,view, MySharedPreferences.AD_Banner!!)
//                }
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnBannerAdsError()
            }

            override fun onAdLoaded(p0: Ad?) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnBannerAdsShow()
            }

            override fun onAdClicked(p0: Ad?) {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnBannerAdsClick()
            }

            override fun onLoggingImpression(p0: Ad?) {

            }


        }
        val loadAdConfig = madView.buildLoadAdConfig().withAdListener(adListener).build()
        madView.loadAd(loadAdConfig)
    }
    internal fun Context.loadFBAdaptiveBannerCustomSize(view: ViewGroup,maxWidth:Int?=0, maxHeight:Int?=0,onCustomBanner: OnCustomBanner) {
        var screenWidth=maxWidth
        var screenHeight=maxHeight
        if (screenWidth==0){
            screenWidth = pxToDp(resources.displayMetrics.widthPixels)
            screenWidth-=5
        }
        if (screenHeight==0){
            screenHeight =pxToDp(resources.displayMetrics.heightPixels)
            screenHeight = screenHeight.div(2)
        }


        val bannerWidth = screenWidth
        val bannerHeight =  screenHeight
        var id = if (Constants.isTestMode) {
            "YOUR_PLACEMENT_ID"
        } else{
            FB_Banner
        }
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds Facebook->$id---${bannerWidth}---$bannerHeight}")
//        val madView = com.facebook.ads.AdView(this, id, com.facebook.ads.AdSize.BANNER_HEIGHT_50)

//        val adSize=com.facebook.ads.AdSize.fromWidthAndHeight(320,  50)
        val madView = com.facebook.ads.AdView(this, id,com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250)

        view.addView(madView)
        val adListener: com.facebook.ads.AdListener = object : com.facebook.ads.AdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
//                if (!isShowAdmobAds){
//                    loadAdaptiveBanner(is_SUBSCRIBED,view, MySharedPreferences.AD_Banner!!)
//                }
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Facebook->${p1!!.errorMessage}"
                )
                onCustomBanner.OnBannerAdsError()
            }

            override fun onAdLoaded(p0: Ad?) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnBannerAdsShow()
            }

            override fun onAdClicked(p0: Ad?) {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnBannerAdsClick()
            }

            override fun onLoggingImpression(p0: Ad?) {

            }


        }
        val loadAdConfig = madView.buildLoadAdConfig().withAdListener(adListener).build()
        madView.loadAd(loadAdConfig)
    }
}

private fun hhgetAdSize(activity: Activity): AdSize {

    // Step 2 - Determine the screen width (less decorations) to use for the ad width.
    val display = activity.windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)

    val widthPixels = outMetrics.widthPixels.toFloat()
    val density = outMetrics.density

    val adWidth = (widthPixels / density).toInt()

    // Step 3 - Get adaptive ad size and return for setting on the ad view.
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)

}
//@SuppressLint("NewApi")
//private fun Context.getAdSize(view: ViewGroup): AdSize? {
//    val windowMetrics: WindowMetrics = (this as Activity).windowManager.getCurrentWindowMetrics()
//    val bounds = windowMetrics.bounds
//    var adWidthPixels: Float = view.width.toFloat()
//
//    // If the ad hasn't been laid out, default to the full screen width.
//    if (adWidthPixels == 0f) {
//        adWidthPixels = bounds.width().toFloat()
//    }
//    val density: Float = getResources().displayMetrics.density
//    val adWidth = (adWidthPixels / density).toInt()
//    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
//}
private fun Context.getAdSize(fAdContainer: ViewGroup): AdSize {
    val display =(this as Activity). windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)
    val density = outMetrics.density
    var adWidthPixels = fAdContainer.width.toFloat()
    if (adWidthPixels == 0f) {
        adWidthPixels = outMetrics.widthPixels.toFloat()
    }
    val adWidth = (adWidthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)

}