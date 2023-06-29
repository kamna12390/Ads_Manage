package com.demo.adsmanage.AdsClass

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowMetrics
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.helper.MySharedPreferences
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


object AdaptiveBannerAds {
    val TAG = this.javaClass.simpleName
    @SuppressLint("NewApi")
    fun Context.loadAdaptiveBanner(
        is_SUBSCRIBED: Boolean,
        view: ViewGroup,
        mAD_AbannerID: String
    ) {
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds AdmodID->$mAD_AbannerID--${AdSize.BANNER.height}--${AdSize.BANNER.width}")
        val madView = AdView(this)
        madView.adUnitId = mAD_AbannerID
        madView.setAdSize(getAdSize(view)!!)
//        madView.setAdSize(AdSize.SMART_BANNER)
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
     fun Context.getAdaptiveAdSize(screenWidth: Int): AdSize? {
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
    fun Context.loadFBAdaptiveBanner(
        is_SUBSCRIBED: Boolean,
        view: ViewGroup,
        mAD_AbannerID: String
    ) {
        logD(TAG, "ADSMANAGE  AdaptiveBannerAds Facebook->$mAD_AbannerID---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.height}---${com.facebook.ads.AdSize.BANNER_HEIGHT_50.width}")
        val madView = com.facebook.ads.AdView(this, mAD_AbannerID, com.facebook.ads.AdSize.BANNER_HEIGHT_50)
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