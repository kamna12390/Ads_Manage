package com.demo.adsmanage.AdsClass

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.InterFace.IsShowBannerAds
import com.demo.adsmanage.InterFace.OnCustomBanner
import com.demo.adsmanage.helper.MySharedPreferences
import com.demo.adsmanage.helper.MySharedPreferences.AD_Banner
import com.demo.adsmanage.helper.MySharedPreferences.FB_Banner
import com.demo.adsmanage.helper.displayMetrics
import com.demo.adsmanage.helper.logD
import com.demo.adsmanage.helper.pxToDp
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import org.jetbrains.anko.windowManager


object AdaptiveBannerAds {
    val TAG = this.javaClass.simpleName
    @SuppressLint("NewApi")
    internal   fun Context.loadAdaptiveBanner(
        view: ViewGroup
    ) {
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->${AD_Banner}--${AdSize.BANNER.height}--${AdSize.BANNER.width}")
        var id = if (Constants.isTestMode!!) {
            "ca-app-pub-3940256099942544/6300978111"
        } else{
            AD_Banner
        }
        val madView = AdView(this)
        madView.adUnitId = id!!
        madView.setAdSize(getAdSize(view)!!)
//        madView.setAdSize(AdSize.MEDIUM_RECTANGLE)
//        val screenWidth = resources.displayMetrics.widthPixels
//        val adSize = getAdaptiveAdSize(screenWidth)

//        madView.setAdSize(adSize!!);
//        madView.setAdSize(AdSize(AdSize.BANNER.width,AdSize.BANNER.height))
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
        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.removeAllViews()
        view.addView(madView,params)
    }
    internal   fun Context.loadAdaptiveCustiomBanner(
        view: ViewGroup,isShowBannerAds: IsShowBannerAds,marginWidth:Int,maxHeight:Int,onCustomBanner: OnCustomBanner) {
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->${AD_Banner}--${AdSize.BANNER.height}--${AdSize.BANNER.width}")
        var id = if (Constants.isTestMode!!) {
            "ca-app-pub-3940256099942544/6300978111"
        } else{
//            AD_Banner
            "ca-app-pub-7441144866662686/3939039229"
        }
//        val massize=if (isShowBannerAds==IsShowBannerAds.MEDIUM_RECTANGLE){
//            AdSize.MEDIUM_RECTANGLE
//        }else if (isShowBannerAds==IsShowBannerAds.LEADERBOARD){
//            AdSize.LEADERBOARD
//        }else if (isShowBannerAds==IsShowBannerAds.SMART_BANNER){
//            AdSize.SMART_BANNER
//        }else if (isShowBannerAds==IsShowBannerAds.FULL_BANNER){
//            AdSize.FULL_BANNER
//        }else{
//            AdSize.BANNER
//        }
        val madView = AdView(this)
        madView.adUnitId = id!!
        madView.setAdSize(getAdSize(view,maxHeight,marginWidth))
        madView.loadAd(AdRequest.Builder().build())
        madView.adListener = object : AdListener() {
            override fun onAdClicked() {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Admob"
                )
                onCustomBanner.OnNativeAdsClick()
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
                onCustomBanner.OnNativeAdsShow()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdFailedToLoad:AdaptiveBannerAds->Admob--${p0.message}"
                )
                onCustomBanner.OnNativeAdsError()
            }
        }
        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view.removeAllViews()
        view.addView(madView,params)
    }
    private fun Context.getAdSize(fAdContainer: ViewGroup,marginWidth:Int,maxHeight:Int): AdSize {
        val display =(this as Activity). windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val density = outMetrics.density
        var adWidthPixels = fAdContainer.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }
        var adWidth = (adWidthPixels / density).toInt()
        adWidth -= marginWidth
        return AdSize.getInlineAdaptiveBannerAdSize(adWidth,maxHeight)
    }

    internal   fun Context.getAdaptiveAdSize(screenWidth: Int): AdSize? {
        // Set the ad width based on the screen width
        var adWidth = screenWidth
        // Set the ad height to wrap content
        var adHeight = ViewGroup.LayoutParams.WRAP_CONTENT

        // Define breakpoints and corresponding ad sizes
        if (screenWidth >= 1024) {
            // Large screen (e.g., tablets)
            adWidth = 728
            adHeight = 90
        } else if (screenWidth >= 640) {
            // Medium screen (e.g., larger smartphones)
            adWidth = 468

            adHeight = 60
        }
         val aa = (displayMetrics.widthPixels / resources.displayMetrics.density).toInt()
         logD(TAG,"ooo-$adWidth-$adHeight--$aa==${pxToDp(displayMetrics.widthPixels)}----${displayMetrics.widthPixels}")
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this,pxToDp(displayMetrics.widthPixels))
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

    internal fun Context.loadFBCustomAdaptiveBanner(
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
                onCustomBanner.OnNativeAdsError()
            }

            override fun onAdLoaded(p0: Ad?) {
                logD(
                    TAG,
                    "ADSMANAGE: onAdLoaded:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnNativeAdsShow()
            }

            override fun onAdClicked(p0: Ad?) {
                isAdsClicking =true
                logD(
                    TAG,
                    "ADSMANAGE: onAdClicked:AdaptiveBannerAds->Facebook"
                )
                onCustomBanner.OnNativeAdsClick()
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