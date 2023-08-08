package com.demo.adsmanage.AdsClass
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.mNativeAdlist
import com.demo.adsmanage.InterFace.NativeAD
import com.demo.adsmanage.InterFace.OnNativeAds
import com.demo.adsmanage.R
import com.demo.adsmanage.helper.MySharedPreferences.AD_NativeAds
import com.demo.adsmanage.helper.MySharedPreferences.FB_NativeAds
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.AdOptionsView
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAdLayout
import com.facebook.ads.NativeAdListener
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView


object NativeAds {
    val TAG = this.javaClass.simpleName
    internal  fun Context.loadNativeAd(
        adsNative: ViewGroup,
        mlayout: Int,
        mfbLayout:Int,
        nativeAD: NativeAD,
        onNativeAds: OnNativeAds
    ) {
        var id = if (Constants.isTestMode!!) {
            "ca-app-pub-3940256099942544/2247696110"
        } else{
            AD_NativeAds
        }
        logD(TAG, "ADSMANAGE  NativeAdID Admob->$id")
        if (mNativeAdlist == null) {
            val builder = AdLoader.Builder(this, id!!)
            builder.forNativeAd { nativeAd ->
                mNativeAdlist = nativeAd
                val mview =
                    LayoutInflater.from(this).inflate(mlayout, null) as NativeAdView
                populateNativeAdView((mNativeAdlist as NativeAd), mview, nativeAD)
                adsNative.removeAllViews()
                adsNative.addView(mview)
                onNativeAds.OnNativeAdsShow()
            }
            val videoOptions =
                VideoOptions.Builder().setStartMuted(true).build()
            val adOptions = NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
            builder.withNativeAdOptions(adOptions)

            val adLoader =
                builder
                    .withAdListener(
                        object : AdListener() {
                            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                                mNativeAdlist = null
                                logD(TAG, "ADSMANAGE: onAdFailedToLoad:NativeAd->AdMob ")
                                if (isShowAdmobAds){
                                    loadFBNativeAd( adsNative, mlayout,mfbLayout, nativeAD, onNativeAds
                                    )
                                }

                                onNativeAds.OnNativeAdsError()
                            }

                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                logD(
                                    TAG,
                                    "ADSMANAGE: onAdLoaded:NativeAd->AdMob"
                                )
                            }

                            override fun onAdClicked() {
                                super.onAdClicked()
                                logD(TAG, "ADSMANAGE: onAdClicked:NativeAd->AdMob ")
                                isAdsClicking=true
                                mNativeAdlist = null
                                if (isShowAdmobAds){
                                    loadNativeAd(
                                        adsNative,
                                        mlayout,
                                        mfbLayout,
                                        nativeAD,
                                        onNativeAds
                                    )
                                }else{
                                    loadFBNativeAd(adsNative, mlayout,mfbLayout, nativeAD, onNativeAds
                                    )
                                }

                                onNativeAds.OnNativeAdsClick()
                            }
                        }
                    )
                    .build()

            adLoader.loadAd(AdRequest.Builder().build())
        } else {
            val unifiedAdBinding =
                LayoutInflater.from(this).inflate(mlayout, null) as NativeAdView
            populateNativeAdView((mNativeAdlist as NativeAd), unifiedAdBinding, nativeAD)
            adsNative.removeAllViews()
            adsNative.addView(unifiedAdBinding)
            onNativeAds.OnNativeAdsShow()
        }

    }

    internal  fun Context.loadFBNativeAd(
        adsNative: ViewGroup,
        mlayout: Int,
        mfbLayout:Int,
        nativeAD: NativeAD,
        onNativeAds: OnNativeAds
    ) {
        var id = if (Constants.isTestMode!!) {
            "YOUR_PLACEMENT_ID"
        } else{
            FB_NativeAds
        }
        logD(TAG, "ADSMANAGE  NativeAdID Admob->$id")

        val nativead = com.facebook.ads.NativeAd(this, id)
        val nativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                logD(TAG, "ADSMANAGE: onAdFailedToLoad:NativeAd->Facebook--${p1!!.errorMessage} ")
                mNativeAdlist = null
                if (!isShowAdmobAds){
                    loadNativeAd(adsNative, mlayout,mfbLayout, nativeAD, onNativeAds)
                }
                onNativeAds.OnNativeAdsError()
            }

            override fun onAdLoaded(p0: Ad?) {
                logD(TAG, "ADSMANAGE: onAdLoaded:NativeAd->Facebook ")

                val mview =
                    LayoutInflater.from(this@loadFBNativeAd).inflate(
                        mfbLayout,
                        null
                    )
                FBinflateAd(nativead, mview as NativeAdLayout, nativeAD)
                adsNative.removeAllViews()
                adsNative.addView(mview)
                onNativeAds.OnNativeAdsShow()


            }

            override fun onAdClicked(p0: Ad?) {
                mNativeAdlist = null
                isAdsClicking=true
                if (isShowAdmobAds){
                    loadNativeAd(
                        adsNative,
                        mlayout,
                        mfbLayout,
                        nativeAD,
                        onNativeAds
                    )
                }else{
                    loadFBNativeAd(adsNative, mlayout,mfbLayout, nativeAD, onNativeAds
                    )
                }

                logD(TAG, "ADSMANAGE: onAdClicked:NativeAd->Facebook ")
            }

            override fun onLoggingImpression(p0: Ad?) {

            }

            override fun onMediaDownloaded(p0: Ad?) {

            }

        }
        nativead.loadAd(nativead.buildLoadAdConfig().withAdListener(nativeAdListener).build())
    }

    internal   fun Context.FBinflateAd(
        nativeAd: com.facebook.ads.NativeAd,
        adView: com.facebook.ads.NativeAdLayout,
        nativeAD: NativeAD
    ) {
        nativeAd.unregisterView()

        // Add the AdOptionsView
        val adChoicesContainer: LinearLayout = adView.findViewById(R.id.ad_choices_container)
        val adOptionsView =
            AdOptionsView(this, nativeAd, adView.findViewById(R.id.native_ad_container))
        adChoicesContainer.removeAllViews()
        adChoicesContainer.addView(adOptionsView, 0)

        // Create native UI using the ad metadata.
        val nativeAdIcon: MediaView = adView.findViewById(R.id.native_ad_icon)
        val nativeAdTitle: TextView = adView.findViewById(R.id.native_ad_title)
        val nativeAdMedia: MediaView = adView.findViewById(R.id.native_ad_media)
        val nativeAdSocialContext: TextView = adView.findViewById(R.id.native_ad_social_context)
        val nativeAdBody: TextView = adView.findViewById(R.id.native_ad_body)
        val sponsoredLabel: TextView = adView.findViewById(R.id.native_ad_sponsored_label)
        val nativeAdCallToAction: Button = adView.findViewById(R.id.native_ad_call_to_action)
        if (nativeAD == NativeAD.NariveBanner) {
            nativeAdMedia.visibility = View.GONE
            adView.findViewById<LinearLayout>(R.id.ln_three).visibility=View.GONE
        }
        // Setting  the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName())
        nativeAdBody.setText(nativeAd.getAdBodyText())
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext())
        nativeAdCallToAction.visibility =
            if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
        nativeAdCallToAction.text = nativeAd.adCallToAction
        sponsoredLabel.text = nativeAd.sponsoredTranslation

        // Create a list of clickable views
        val clickableViews: MutableList<View> = ArrayList()
        clickableViews.add(nativeAdTitle)
        clickableViews.add(nativeAdCallToAction)

        // Register the Title and  button to listen for clicks.
        nativeAd.registerViewForInteraction(
            adView, nativeAdMedia, nativeAdIcon, clickableViews
        )
    }

    private fun populateNativeAdView(
        nativeAd: NativeAd,
        nativeAdView: NativeAdView,
        nativeAD: NativeAD
    ) {
        val nativeAdView = nativeAdView

        // Set the media view.
        nativeAdView.mediaView = nativeAdView.findViewById(R.id.ad_media)
        // Set other ad assets.
        nativeAdView.headlineView = nativeAdView.findViewById(R.id.ad_headline)
        nativeAdView.bodyView = nativeAdView.findViewById(R.id.ad_body)
        nativeAdView.callToActionView = nativeAdView.findViewById(R.id.ad_call_to_action)
        nativeAdView.iconView = nativeAdView.findViewById(R.id.ad_app_icon)
        nativeAdView.priceView = nativeAdView.findViewById(R.id.ad_price)
        nativeAdView.starRatingView = nativeAdView.findViewById(R.id.ad_stars)
        nativeAdView.storeView = nativeAdView.findViewById(R.id.ad_store)
        nativeAdView.advertiserView = nativeAdView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (nativeAdView.headlineView as TextView).text = nativeAd.headline
        nativeAd.mediaContent?.let { nativeAdView.mediaView!!.setMediaContent(it) }

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            nativeAdView.bodyView!!.visibility = View.INVISIBLE
        } else {
            nativeAdView.bodyView!!.visibility = View.VISIBLE
            (nativeAdView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            nativeAdView.callToActionView!!.visibility = View.INVISIBLE
        } else {
            nativeAdView.callToActionView!!.visibility = View.VISIBLE
            (nativeAdView.callToActionView as AppCompatTextView).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            nativeAdView.iconView!!.visibility = View.GONE
        } else {
            (nativeAdView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
            nativeAdView.iconView!!.visibility = View.VISIBLE
        }
        if (nativeAD == NativeAD.NativeFull) {
            if (nativeAd.price == null) {
                nativeAdView.priceView!!.visibility = View.INVISIBLE
            } else {
                nativeAdView.priceView!!.visibility = View.VISIBLE
                (nativeAdView.priceView as TextView).text = nativeAd.price
            }
            if (nativeAd.store == null) {
                nativeAdView.storeView!!.visibility = View.INVISIBLE
            } else {
                nativeAdView.storeView!!.visibility = View.VISIBLE
                (nativeAdView.storeView as TextView).text = nativeAd.store
            }
        }




        if (nativeAd.starRating == null) {
            nativeAdView.starRatingView!!.visibility = View.INVISIBLE
        } else {
            (nativeAdView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            nativeAdView.starRatingView!!.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            nativeAdView.advertiserView!!.visibility = View.INVISIBLE
        } else {
            (nativeAdView.advertiserView as TextView).text = nativeAd.advertiser
            nativeAdView.advertiserView!!.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        nativeAdView.setNativeAd(nativeAd)
    }
}