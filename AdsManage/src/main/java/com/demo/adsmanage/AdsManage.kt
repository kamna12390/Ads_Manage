package com.demo.adsmanage


import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.view.ViewGroup
import android.widget.Toast
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadAdaptiveBanner
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadFBAdaptiveBanner
import com.demo.adsmanage.AdsClass.AppOpenAds.loadAppOpenAd
import com.demo.adsmanage.AdsClass.AppOpenAds.loadFirsttimeAppOpenAd
import com.demo.adsmanage.AdsClass.AppOpenAds.showAppOpenAd
import com.demo.adsmanage.AdsClass.InterstitialAds.loadFBInterstitialSd
import com.demo.adsmanage.AdsClass.InterstitialAds.loadInterstitialAd
import com.demo.adsmanage.AdsClass.InterstitialAds.showInterstitialAd
import com.demo.adsmanage.AdsClass.NativeAds.loadFBNativeAd
import com.demo.adsmanage.AdsClass.NativeAds.loadNativeAd
import com.demo.adsmanage.AdsClass.RewardedAds.loadFBRewatdedAD
import com.demo.adsmanage.AdsClass.RewardedAds.loadRewardedAds
import com.demo.adsmanage.AdsClass.RewardedAds.showRewarded
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.APP_OPEN_AD_ORIENTATION_LANDSCAPE
import com.demo.adsmanage.Commen.Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT
import com.demo.adsmanage.Commen.Constants.Noads
import com.demo.adsmanage.Commen.Constants.editor
import com.demo.adsmanage.Commen.Constants.isCreationNativeShow
import com.demo.adsmanage.Commen.Constants.isHomeNativeShow
import com.demo.adsmanage.Commen.Constants.isSettingNativeShow
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.isTestMode
import com.demo.adsmanage.Commen.Constants.is_ProgressShow
import com.demo.adsmanage.Commen.Constants.mAppOpenAds
import com.demo.adsmanage.Commen.Constants.mAppOpenAds_LANDSCAPE
import com.demo.adsmanage.Commen.Constants.mInterstitialAdlist
import com.demo.adsmanage.Commen.Constants.mInterstitialAds_clickCount
import com.demo.adsmanage.Commen.Constants.mPreferences
import com.demo.adsmanage.InterFace.NativeAD
import com.demo.adsmanage.InterFace.OnAppOpenShowAds
import com.demo.adsmanage.InterFace.OnInterAdsShowAds
import com.demo.adsmanage.InterFace.OnInterstitialAds
import com.demo.adsmanage.InterFace.OnNativeAds
import com.demo.adsmanage.InterFace.OnRewardedShowAds
import com.demo.adsmanage.InterFace.OnSplachAds
import com.demo.adsmanage.helper.MySharedPreferences.AD_AppOpen
import com.demo.adsmanage.helper.MySharedPreferences.AD_Banner
import com.demo.adsmanage.helper.MySharedPreferences.AD_Interstitial
import com.demo.adsmanage.helper.MySharedPreferences.AD_NativeAds
import com.demo.adsmanage.helper.MySharedPreferences.AD_RewardedAds
import com.demo.adsmanage.helper.MySharedPreferences.FB_Banner
import com.demo.adsmanage.helper.MySharedPreferences.FB_Interstitial
import com.demo.adsmanage.helper.MySharedPreferences.FB_NativeAds
import com.demo.adsmanage.helper.MySharedPreferences.FB_RewardedAds
import com.demo.adsmanage.helper.MySharedPreferences.Interstitial_CountShow
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.demo.adsmanage.helper.misOnline
import com.demo.adsmanage.model.AdsModel
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.google.gson.GsonBuilder


object AdsManage {
    public class ActivityBuilder() : Builder()
    val TAG=this.javaClass.simpleName
    private val COUNTER_TIME = 2L
    private var mcountRemaining: Long = 0L
    var dialog_ad:ProgressDialog?=null
    val configSettings: FirebaseRemoteConfigSettings
        get() {
            return FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0)
                .build()
        }
    val mFirebaseRemoteConfig: FirebaseRemoteConfig
        get() {
            return FirebaseRemoteConfig.getInstance()
        }

    abstract class Builder()  {
        fun ApplicationCall(application: Application){
            mPreferences = application.getSharedPreferences("MyAdsClass", Context.MODE_PRIVATE)
            editor = mPreferences!!.edit()
        }
        fun Splash_Init(context: Context,firebasename:String,onSplachAds: OnSplachAds) {
            with(context){

                if (misOnline) {
                    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                    mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful){
                            val mobject= mFirebaseRemoteConfig.getString(firebasename)
                            val gson: Gson = GsonBuilder().create()
                            val lessons: AdsModel = gson.fromJson(
                                mobject,
                                AdsModel::class.java
                            )
                            with(lessons.appChanging!!){
                                isShowAdmobAds = misShowAdmobAds!!
                                isTestMode=testAdsShow!!
                                mInterstitialAds_clickCount= interstitialClickCountShow!!
                                Interstitial_CountShow=interstitialClickCountShow
                                isHomeNativeShow=misHomeNativeShow!!
                                isSettingNativeShow=misSettingNativeShow!!
                                isCreationNativeShow=misCreationNativeShow!!
                                is_ProgressShow=misProgressShow!!
                            }

                            with(lessons.appChanging.admob!!){
                                AD_Interstitial=adInterstitial!!
                                AD_Banner=adBanner
                                AD_AppOpen=adAppOpen
                                AD_NativeAds=adNativeAds!!
                                AD_RewardedAds=adRewardedAds

                            }
                            with(lessons.appChanging.faceBook!!){
                                FB_Interstitial=fbInterstitial
                                FB_Banner=fbBanner
                                FB_NativeAds=fbNativeAds
                                FB_RewardedAds=fbRewardedAds
                            }
                            loadFirsttimeAppOpenAd(false, AD_AppOpen!!,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,onSplachAds)

//                            onSplachAds.OnNextAds()
                            logD(TAG, "$AD_Interstitial==$AD_Banner")
                        }else{

                        }
                    })
                } else {
                    onSplachAds.OnNextAds()
//                    Toast.makeText(this, "Please connect internet", Toast.LENGTH_LONG).show()
                }
            }
        }

        fun Show_AdaptiveBanner(context: Context,is_SUBSCRIBED: Boolean, view:ViewGroup){
            with(context){
                if (isOnline && !is_SUBSCRIBED){
                    if (isShowAdmobAds && AD_Banner!=null && AD_Banner!=Noads){
                        loadAdaptiveBanner(view)
                    }else {
                        if(FB_Banner==null && FB_Banner==Noads){
                            return
                        }
                        loadFBAdaptiveBanner(view)
                    }
                }

            }
        }
        fun Load_HOME_NativeAds(context: Context,is_SUBSCRIBED: Boolean,adsNative: ViewGroup,mlayout:Int,mfbLayout:Int,nativeAD: NativeAD,onNativeAds: OnNativeAds){
            with(context){
                if (isOnline && !is_SUBSCRIBED){
                    if (isHomeNativeShow){
                        if (isShowAdmobAds && AD_NativeAds!=null && AD_NativeAds!=Noads){
                            loadNativeAd(adsNative,mlayout,mfbLayout,nativeAD,onNativeAds)
                        }else{
                            if(FB_NativeAds==null && FB_NativeAds==Noads){
                                return
                            }
                            loadFBNativeAd( adsNative,  mlayout,mfbLayout, nativeAD, onNativeAds)
                        }
                    }else{
                        onNativeAds.OnNativeAdsError()
                    }
                }
            }


        }
        fun Load_SETTING_NativeAds(context: Context,is_SUBSCRIBED: Boolean,adsNative: ViewGroup,mlayout:Int,mfbLayout:Int,nativeAD: NativeAD,onNativeAds: OnNativeAds){
            with(context){
                if (isOnline && !is_SUBSCRIBED){

                    if (isSettingNativeShow){
                        if (isShowAdmobAds && AD_NativeAds!=null && AD_NativeAds!=Noads){
                            loadNativeAd(adsNative,mlayout,mfbLayout,nativeAD,onNativeAds)
                        }else{
                            if(FB_NativeAds==null && FB_NativeAds==Noads){
                                return
                            }
                            loadFBNativeAd( adsNative, mlayout,mfbLayout, nativeAD, onNativeAds)
                        }

                    }else{
                        onNativeAds.OnNativeAdsError()
                    }
                }
            }


        }
        fun Load_CREATION_NativeAds(context: Context,is_SUBSCRIBED: Boolean,adsNative: ViewGroup,mlayout:Int,mfbLayout:Int,nativeAD: NativeAD,onNativeAds: OnNativeAds){
            with(context){
                if (isOnline && !is_SUBSCRIBED){

                    if (isCreationNativeShow){
                        if (isShowAdmobAds && AD_NativeAds!=null && AD_NativeAds!=Noads){
                            loadNativeAd(adsNative,mlayout,mfbLayout,nativeAD,onNativeAds)
                        }else{
                            if(FB_NativeAds==null && FB_NativeAds==Noads){
                                return
                            }
                            loadFBNativeAd(adsNative, mlayout,mfbLayout, nativeAD, onNativeAds)
                        }
                    }else{
                        onNativeAds.OnNativeAdsError()
                    }
                }
            }


        }
        fun Load_InterstitialAd(context: Context,is_SUBSCRIBED: Boolean) {
            with(context){
                if (isOnline && !is_SUBSCRIBED){

                    if (isShowAdmobAds && AD_Interstitial!=null && AD_Interstitial!=Noads){
                        loadInterstitialAd()
                    }else{
                        if (FB_Interstitial==null && FB_Interstitial==Noads){
                            return
                        }
                        loadFBInterstitialSd()
                    }
                }
            }


        }
        fun Show_InterstitialAds(context: Context,is_SUBSCRIBED: Boolean,intent: Intent?=null){
            with(context){
                if (isOnline && !is_SUBSCRIBED){
                    if (Interstitial_CountShow!! == mInterstitialAds_clickCount){
                        mInterstitialAds_clickCount=0
                        if (is_ProgressShow){
                            showDialog()
                            val countDownTimer: CountDownTimer = object : CountDownTimer(COUNTER_TIME * 1000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    mcountRemaining = millisUntilFinished / 1000 + 1
                                }

                                override fun onFinish() {
                                    mcountRemaining = 0
                                    if (mInterstitialAdlist!=null || FB_Interstitial!=null){
                                        dismiss()
                                        showInterstitialAd(is_SUBSCRIBED,object : OnInterstitialAds {
                                            override fun OnDismissAds() {
                                                NextScreen(intent)
                                            }

                                            override fun OnError() {
                                                NextScreen(intent)
                                            }
                                        })
                                    }else{
                                        dismiss()
                                        NextScreen(intent)
                                    }
                                }
                            }
                            countDownTimer.start()
                        }else{
                            if (mInterstitialAdlist!=null || FB_Interstitial!=null){
                                showInterstitialAd(is_SUBSCRIBED,object : OnInterstitialAds {
                                    override fun OnDismissAds() {
                                        NextScreen(intent)
                                    }

                                    override fun OnError() {
                                        NextScreen(intent)
                                    }
                                })
                            }else{
                                NextScreen(intent)
                            }
                        }


                    }
                    else{
                        mInterstitialAds_clickCount++
                        NextScreen(intent)
                    }
                }else{
                    NextScreen(intent)
                }
            }
        }
        fun Show_InterstitialInterfaceAds(context: Context,is_SUBSCRIBED: Boolean,onInterAdsShowAds: OnInterAdsShowAds){
            with(context){
                if (isOnline && !is_SUBSCRIBED){

                    if (Interstitial_CountShow!! == mInterstitialAds_clickCount){
                        mInterstitialAds_clickCount=0
                        if (is_ProgressShow){
                            showDialog()
                            val countDownTimer: CountDownTimer = object : CountDownTimer(COUNTER_TIME * 1000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    mcountRemaining = millisUntilFinished / 1000 + 1
                                }

                                override fun onFinish() {
                                    mcountRemaining = 0
                                    if (mInterstitialAdlist!=null || FB_Interstitial!=null){
                                        dismiss()
                                        showInterstitialAd(is_SUBSCRIBED,object : OnInterstitialAds {
                                            override fun OnDismissAds() {
                                                onInterAdsShowAds.OnDismissAds()
                                            }

                                            override fun OnError() {
                                                onInterAdsShowAds.OnError()
                                            }
                                        })
                                    }else{
                                        dismiss()
                                        onInterAdsShowAds.OnError()
                                    }
                                }
                            }
                            countDownTimer.start()
                        }else{
                            if (mInterstitialAdlist!=null || FB_Interstitial!=null){
                                showInterstitialAd(is_SUBSCRIBED,object : OnInterstitialAds {
                                    override fun OnDismissAds() {
                                        onInterAdsShowAds.OnDismissAds()
                                    }

                                    override fun OnError() {
                                        onInterAdsShowAds.OnError()
                                    }
                                })
                            }else{
                                onInterAdsShowAds.OnError()
                            }
                        }


                    }
                    else{
                        mInterstitialAds_clickCount++
                        onInterAdsShowAds.OnDismissAds()
                    }
                }else{
                    onInterAdsShowAds.OnDismissAds()
                }
            }
        }
        fun Context.NextScreen(intent: Intent?){
            if (intent!=null){
                startActivity(intent)
            }
        }
        fun Load_AppOpenAd(context: Context,is_SUBSCRIBED: Boolean,appOpenAd:Int){
            with(context){
                if (isOnline && AD_AppOpen!=null && !is_SUBSCRIBED && AD_AppOpen!=Noads){
                    loadAppOpenAd(is_SUBSCRIBED, AD_AppOpen!!, appOpenAd)
                }
            }
        }
        fun Show_AppOpenAd(context: Context,is_SUBSCRIBED: Boolean,appOpenAd: Int,onAppOpenShowAds: OnAppOpenShowAds){
            with(context){
                if (isOnline &&  !is_SUBSCRIBED ){
                    if (appOpenAd == APP_OPEN_AD_ORIENTATION_PORTRAIT && mAppOpenAds !=null){
                        showAppOpenAd(appOpenAd,onAppOpenShowAds)
                    }else  if (appOpenAd == APP_OPEN_AD_ORIENTATION_LANDSCAPE && mAppOpenAds_LANDSCAPE !=null){
                        showAppOpenAd(appOpenAd,onAppOpenShowAds)
                    }else{
                        onAppOpenShowAds.OnDismissAds()
                    }
                }else{
                    onAppOpenShowAds.OnDismissAds()
                }

            }
        }

        fun Load_RewardedAd(context: Context,is_SUBSCRIBED: Boolean){
            with(context){
                if (isOnline && !is_SUBSCRIBED){

                    if (isShowAdmobAds && AD_RewardedAds!=null && AD_RewardedAds!=Noads){
                        loadRewardedAds(is_SUBSCRIBED)
                    }else{
                        if (FB_RewardedAds==null && FB_RewardedAds==Noads){
                            return
                        }
                        loadFBRewatdedAD(is_SUBSCRIBED)
                    }
                }
            }
        }
        fun Show_RewardedAd(context: Context,is_SUBSCRIBED: Boolean,onRewardedShowAds: OnRewardedShowAds){
            with(context){
                if (isOnline){
                    if (is_SUBSCRIBED){
                        onRewardedShowAds.OnUserEarned()
                        return
                    }
                    showDialog()
                    val countDownTimer: CountDownTimer = object : CountDownTimer(COUNTER_TIME * 1000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            mcountRemaining = millisUntilFinished / 1000 + 1
                        }

                        override fun onFinish() {
                            mcountRemaining = 0
                            dismiss()
                            showRewarded(is_SUBSCRIBED,onRewardedShowAds)
                        }
                    }
                    countDownTimer.start()
                }else{
                    onRewardedShowAds.OnError()
                }
            }
        }
        fun Context.showDialog() {
            dialog_ad = ProgressDialog(this)
            dialog_ad!!.setCancelable(false)
            dialog_ad!!.setCanceledOnTouchOutside(false)
            dialog_ad!!.setTitle(this.getString(R.string.please_wait))
            dialog_ad!!.setMessage(this.getString(R.string.load_ad))
            dialog_ad!!.show()

        }
        fun Context.dismiss(){
            if (dialog_ad!=null){
                dialog_ad!!.dismiss()
            }

        }
    }


}