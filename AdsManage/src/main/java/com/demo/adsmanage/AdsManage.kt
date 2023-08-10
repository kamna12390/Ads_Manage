package com.demo.adsmanage


import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.ViewGroup
import com.demo.adsmanage.Activity.SubscriptionBackgroundActivity
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadAdaptiveBanner
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadAdaptiveCustiomBanner
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadFBAdaptiveBanner
import com.demo.adsmanage.AdsClass.AdaptiveBannerAds.loadFBCustomAdaptiveBanner
import com.demo.adsmanage.AdsClass.AppOpenAds.loadAppOpenAd
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
import com.demo.adsmanage.Commen.Constants.BASIC_SKU
import com.demo.adsmanage.Commen.Constants.BaseSubscriptionBackground
import com.demo.adsmanage.Commen.Constants.NavigationBarColor
import com.demo.adsmanage.Commen.Constants.Noads
import com.demo.adsmanage.Commen.Constants.PREMIUM_SIX_SKU
import com.demo.adsmanage.Commen.Constants.PREMIUM_SKU
import com.demo.adsmanage.Commen.Constants.Purchase_ID
import com.demo.adsmanage.Commen.Constants.SUBButtonTextColor
import com.demo.adsmanage.Commen.Constants.SubscriptionBackground
import com.demo.adsmanage.Commen.Constants.editor
import com.demo.adsmanage.Commen.Constants.isCreationNativeShow
import com.demo.adsmanage.Commen.Constants.isHomeNativeShow
import com.demo.adsmanage.Commen.Constants.isSettingNativeShow
import com.demo.adsmanage.Commen.Constants.isShowAdmobAds
import com.demo.adsmanage.Commen.Constants.isTestMode
import com.demo.adsmanage.Commen.Constants.is_ABTest
import com.demo.adsmanage.Commen.Constants.is_BackAdsShow
import com.demo.adsmanage.Commen.Constants.is_ProgressShow
import com.demo.adsmanage.Commen.Constants.mAppIcon
import com.demo.adsmanage.Commen.Constants.mAppName
import com.demo.adsmanage.Commen.Constants.mAppNameColor
import com.demo.adsmanage.Commen.Constants.mAppOpenAds
import com.demo.adsmanage.Commen.Constants.mAppOpenAds_LANDSCAPE
import com.demo.adsmanage.Commen.Constants.mBasic_Line_Icon
import com.demo.adsmanage.Commen.Constants.mClose_Icon
import com.demo.adsmanage.Commen.Constants.mInterstitialAdlist
import com.demo.adsmanage.Commen.Constants.mInterstitialAds_clickCount
import com.demo.adsmanage.Commen.Constants.mIsRevenuCat
import com.demo.adsmanage.Commen.Constants.mMainLineColor
import com.demo.adsmanage.Commen.Constants.mPreferences
import com.demo.adsmanage.Commen.Constants.mPremiumLine
import com.demo.adsmanage.Commen.Constants.mPremiumScreenLine
import com.demo.adsmanage.Commen.Constants.mPremium_Button_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_CardSelected_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_Cardunselected_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_True_Icon
import com.demo.adsmanage.Commen.Constants.mPriceBackground
import com.demo.adsmanage.Commen.Constants.mPriceLineColor
import com.demo.adsmanage.Commen.Constants.mPrivacyPolicyURL
import com.demo.adsmanage.Commen.Constants.mSmallSubLineColor
import com.demo.adsmanage.Commen.Constants.mSplashDelayTime
import com.demo.adsmanage.Commen.Constants.mSubLineColor
import com.demo.adsmanage.Commen.Constants.misSubscription
import com.demo.adsmanage.Commen.Constants.packagerenlist
import com.demo.adsmanage.InterFace.IsShowBannerAds
import com.demo.adsmanage.InterFace.IsSplashShowAds
import com.demo.adsmanage.InterFace.NativeAD
import com.demo.adsmanage.InterFace.OnAppOpenShowAds
import com.demo.adsmanage.InterFace.OnCustomBanner
import com.demo.adsmanage.InterFace.OnInterAdsShowAds
import com.demo.adsmanage.InterFace.OnInterstitialAds
import com.demo.adsmanage.InterFace.OnNativeAds
import com.demo.adsmanage.InterFace.OnRewardedShowAds
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.adsmanage.billing.ProductPurchaseHelper.setSubscriptionKey
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
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.getOfferingsWith
import java.text.SimpleDateFormat
import java.util.Date


object AdsManage {

    val TAG="AdsManageclassTAG"
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


    public class ActivityBuilder() :
        Builder() {
        override fun Subcall(context: Context): Builder {
            if (mIsRevenuCat!!){
                Purchases.debugLogsEnabled = true
                Purchases.configure(
                    PurchasesConfiguration.Builder(context, Purchase_ID).build()
                )
                Purchases.sharedInstance.getOfferingsWith({ error ->
                    // An error occurred
                    logD("SubscriptionList", "error->${error.message}")
                }) { offerings ->
                    offerings.current?.availablePackages?.takeUnless { it.isNullOrEmpty() }?.let {
                        // Display packages for sale
//                    logD(
//                        "yagnik",
//                        "suc-> 1 ${"originalPrice :- " + it[0].product.originalPrice + "\n" + "freeTrialPeriod :- " + it[0].product.freeTrialPeriod + "\n" + "title :- " + it[0].product.title + "\n" + "price :- " + it[0].product.price + "\n" + "description :- " + it[0].product.description + "\n" + "subscriptionPeriod :- " + it[0].product.subscriptionPeriod + "\n" + "sku :- " + it[0].product.sku + "\n"}"
//                    )
                        logD("SubscriptionList", "1->${it[0].product.sku}--2->${it[1].product.sku}--3->${it[0].product.sku}")
                        packagerenlist?.clear()
                        Constants.BASIC_SKU = it[0].product.sku
                        Constants.PREMIUM_SKU = it[1].product.sku
                        Constants.PREMIUM_SIX_SKU = it[0].product.sku

                        packagerenlist = arrayListOf()
                        packagerenlist?.add(
                            Constants.PackagesRen(
                                it[0].product.originalPrice.toString(),
                                it[0].product.freeTrialPeriod.toString(),
                                it[0].product.title,
                                it[0].product.price,
                                it[0].product.description,
                                it[0].product.subscriptionPeriod.toString(),
                                it[0].product.sku
                            )
                        )
                        packagerenlist?.add(
                            Constants.PackagesRen(
                                it[1].product.originalPrice.toString(),
                                it[1].product.freeTrialPeriod.toString(),
                                it[1].product.title,
                                it[1].product.price,
                                it[1].product.description,
                                it[1].product.subscriptionPeriod.toString(),
                                it[1].product.sku
                            )
                        )
//                    logD(
//                        "yagnik",
//                        "suc-> 2 ${"originalPrice :- " + it[1].product.originalPrice + "\n" + "freeTrialPeriod :- " + it[1].product.freeTrialPeriod + "\n" + "title :- " + it[1].product.title + "\n" + "price :- " + it[1].product.price + "\n" + "description :- " + it[1].product.description + "\n" + "subscriptionPeriod :- " + it[1].product.subscriptionPeriod + "\n" + "sku :- " + it[1].product.sku + "\n"}"
//                    )

                    }
                }
            }else{
                setSubscriptionKey(BASIC_SKU, PREMIUM_SIX_SKU, PREMIUM_SKU)
            }
            return this
        }
    }
    abstract class Builder()  {

        fun ApplicationCall(application: Application):Builder{
            mPreferences = application.getSharedPreferences("MyAdsClass", Context.MODE_PRIVATE)
            editor = mPreferences!!.edit()
            return this
        }
        fun setIsRevenuCat(boolean: Boolean): Builder {
            mIsRevenuCat = boolean
            return this
        }
        fun setRevenuCatPurchase_ID(string: String): Builder {
            Purchase_ID=string
            return this
        }
        fun setPREMIUM_SIX_SKU(string: String): Builder {
            PREMIUM_SIX_SKU=string
            return this
        }
        fun setPREMIUM_SKU(string: String): Builder {
            PREMIUM_SKU=string
            return this
        }
        fun setBASIC_SKU(string: String): Builder {
            BASIC_SKU=string
            return this
        }
        fun setPrivacyPolicyURL(string: String): Builder {
            mPrivacyPolicyURL = string
            return this
        }
        fun setAppName(AppName: String, color: Int): Builder {
            mAppName = AppName
            mAppNameColor=color
            return this
        }
        fun setBackgroundSubscreenLineColor(color: Int): Builder {
            mMainLineColor=color
            return this
        }
        fun setSubLineColor(color: Int): Builder {
            mSubLineColor=color
            return this
        }
        fun setSmallSubLineColor(color: Int): Builder {
            mSmallSubLineColor=color
            return this
        }
        fun setSUBButtonTextColor(color: Int): Builder {
            SUBButtonTextColor=color
            return this
        }
        fun setPriceTextColor(color: Int): Builder {
            mPriceLineColor=color
            return this
        }
        fun setNavigationBarColor(color: Int): Builder {
            NavigationBarColor =color
            return this
        }

        fun setAppIcon(drawable: Drawable): Builder {
            mAppIcon = drawable
            return this
        }
        fun setPremium_True_Icon(drawable: Drawable): Builder {
            mPremium_True_Icon = drawable
            return this
        }

        fun setBasic_Line_Icon(drawable: Drawable): Builder {
            mBasic_Line_Icon = drawable
            return this
        }
        fun setBaseSubscriptionBackground(drawable: Drawable): Builder {
            BaseSubscriptionBackground = drawable
            return this
        }
        fun setSubscriptionBackground(drawable: Drawable): Builder {
            SubscriptionBackground = drawable
            return this
        }
        fun setPriceBackground(drawable: Drawable): Builder {
            mPriceBackground = drawable
            return this
        }

        fun setClose_Icon(drawable: Drawable): Builder {
            mClose_Icon = drawable
            return this
        }

        fun setPremium_Button_Icon(drawable: Drawable): Builder {
            mPremium_Button_Icon = drawable
            return this
        }

        fun setPremium_CardSelected_Icon(drawable: Drawable): Builder {
            mPremium_CardSelected_Icon = drawable
            return this
        }

        fun setPremium_Cardunselected_Icon(drawable: Drawable): Builder {
            mPremium_Cardunselected_Icon = drawable
            return this
        }

        fun setBackgroundSubscreenLine(premiumLine: ArrayList<Constants.LineWithIconModel>): Builder {
            mPremiumLine = premiumLine
            return this
        }

        fun setSubScreenOfLine(premiumLine: ArrayList<Constants.LineWithIconModel>): Builder {
            mPremiumScreenLine = premiumLine
            return this
        }

        fun setRevenuCatDefaultList(premiumLine: ArrayList<Constants.PackagesRen>): Builder {
            packagerenlist = premiumLine
            return this
        }
        fun setActivityOpen(boolean: Boolean, application: Application): Builder {
            BaseSharedPreferences(application).mActivityOpen = boolean
            return this
        }
        fun setIsSubscription(isSubscription:Boolean):Builder{
            misSubscription=isSubscription
            return this
        }
        fun setAdmobAdsID(AppOpenID:String?="",BannerAdaptiveID:String?="",InterstitialID:String?="",NativeAdsID:String?="",RewardedID:String?=""): Builder {
            AD_Interstitial=InterstitialID!!
            AD_Banner=BannerAdaptiveID
            AD_AppOpen=AppOpenID
            AD_NativeAds=NativeAdsID!!
            AD_RewardedAds=RewardedID
            return this
        }
        fun setFBAdsID(fbInterstitial:String?="",fbBanner:String?="",fbNativeAds:String?="",fbRewardedAds:String?=""): Builder {
            FB_Interstitial=fbInterstitial
            FB_Banner=fbBanner
            FB_NativeAds=fbNativeAds
            FB_RewardedAds=fbRewardedAds
            return this
        }
        abstract fun Subcall(context: Context): Builder
        fun Splash_Init(context: Context,firebasename:String,mClass:String,SplashDelayTime:Long,isSplashShowAds: IsSplashShowAds) {
            with(context){
                mSplashDelayTime=SplashDelayTime
                val mintent=Intent(context, Class.forName(mClass))
                FirebaseInstallations.getInstance().id
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Installations", "Installation ID: " + task.result)
                        } else {
                            Log.e("Installations", "Unable to get Installation ID")
                        }
                    }
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }
                    // Get new FCM registration token
                    val token = task.result
                    // Log and toast
                    Log.d("Installations", "Token->$token")
                })
                if (misOnline) {
                    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
                    mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(OnCompleteListener {
                        if (it.isSuccessful){
                            val mobject= mFirebaseRemoteConfig.getString(firebasename)
                            if (!mobject.isNullOrBlank()){
                                val gson: Gson = GsonBuilder().create()
                                var lessons: AdsModel?=null
                                try{
                                     lessons = gson.fromJson(
                                        mobject,
                                        AdsModel::class.java
                                    )
                                }catch (e:Exception){

                                }
                                if (lessons==null){
                                    val intent=Intent(context, Class.forName(mClass))
                                    context.NextScreen(intent)
                                    return@OnCompleteListener
                                }
                                logD("ResponseCheck","->$lessons")
                                with(lessons.appChanging!!){
                                    isShowAdmobAds = misShowAdmobAds!!
                                    isTestMode=testAdsShow!!
                                    mInterstitialAds_clickCount= interstitialClickCountShow!!
                                    Interstitial_CountShow=interstitialClickCountShow
                                    isHomeNativeShow=misHomeNativeShow!!
                                    isSettingNativeShow=misSettingNativeShow!!
                                    isCreationNativeShow=misCreationNativeShow!!
                                    is_ProgressShow=misProgressShow!!
                                    is_BackAdsShow=misBackAdsShow!!
                                    is_ABTest=mIs_ABTest!!
//                                    logD("YagnikABtest","->${mIs_ABTest}")
                                }
//                                with(lessons.appChanging!!.admob!!){
//                                    AD_Interstitial=adInterstitial!!
//                                    AD_Banner=adBanner
//                                    AD_AppOpen=adAppOpen
//                                    AD_NativeAds=adNativeAds!!
//                                    AD_RewardedAds=adRewardedAds

//                                }
//                                with(lessons.appChanging!!.faceBook!!){
//                                    FB_Interstitial=fbInterstitial
//                                    FB_Banner=fbBanner
//                                    FB_NativeAds=fbNativeAds
//                                    FB_RewardedAds=fbRewardedAds
//                                }
                                val id=if (isTestMode) {
                                    "ca-app-pub-3940256099942544/3419835294"
                                }else{
                                    AD_AppOpen
                                }

                                if (misSubscription!!){
                                    if (!BaseSharedPreferences(this).mIS_SUBSCRIBED!!){
                                        Load_AppOpenAd(context,false,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT)
                                        Load_InterstitialAd(context,false)
                                        Handler(mainLooper).postDelayed({
                                            mSubscriptionFlow(context,mClass,isSplashShowAds)
                                        }, mSplashDelayTime)
                                    }else{
                                        Handler(mainLooper).postDelayed({
                                            NextScreen(mintent)
                                        }, mSplashDelayTime)
                                    }
                                }else{
                                    SplachToNextScreen(mintent,isSplashShowAds)
                                }

                            }else{
                                SplachToNextScreen(mintent,isSplashShowAds)
                            }

                        }else{
                            SplachToNextScreen(mintent,isSplashShowAds)
                        }
                    })
                } else {
                    SplachToNextScreen(mintent,isSplashShowAds)
                }
            }
        }
        private fun Context.SplachToNextScreen(mintent:Intent,isSplashShowAds: IsSplashShowAds){
            if (isOnline){
                Load_AppOpenAd(this,false,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT)
                Load_InterstitialAd(this,false)
                    Handler(mainLooper).postDelayed({
                        if (isSplashShowAds==IsSplashShowAds.IsInterstitialAdsShow){
                            Show_InterstitialInterfaceAds(this,false,object : OnInterAdsShowAds {
                                override fun OnDismissAds() {
                                    NextScreen(mintent)
                                }
                                override fun OnError() {
                                    NextScreen(mintent)
                                }
                            })
                        }else if (isSplashShowAds==IsSplashShowAds.IsAppOpenAdsORIENTATION_LANDSCAPE){
                            Show_AppOpenAd(this,false, APP_OPEN_AD_ORIENTATION_LANDSCAPE,object : OnAppOpenShowAds {
                                override fun OnDismissAds() {
                                    Load_AppOpenAd(this@SplachToNextScreen,false,APP_OPEN_AD_ORIENTATION_LANDSCAPE)
                                    NextScreen(mintent)
                                }
                                override fun OnError() {
                                    NextScreen(mintent)
                                }
                            })
                        }else{
                            Show_AppOpenAd(this,false, APP_OPEN_AD_ORIENTATION_PORTRAIT,object : OnAppOpenShowAds {
                                override fun OnDismissAds() {
                                    Load_AppOpenAd(this@SplachToNextScreen,false,APP_OPEN_AD_ORIENTATION_PORTRAIT)
                                    NextScreen(mintent)
                                }
                                override fun OnError() {
                                    NextScreen(mintent)
                                }
                            })
                        }
                    }, mSplashDelayTime)
            }else{
                Handler(mainLooper).postDelayed({
                    NextScreen(mintent)
                }, mSplashDelayTime)
            }
        }
        private fun Context.SubToNextScreen(mintent:Intent,isSplashShowAds: IsSplashShowAds){
            if (isSplashShowAds==IsSplashShowAds.IsInterstitialAdsShow){
                Show_InterstitialInterfaceAds(this,BaseSharedPreferences(this).mIS_SUBSCRIBED!!,object : OnInterAdsShowAds {
                    override fun OnDismissAds() {
                        BaseSharedPreferences(this@SubToNextScreen).mOpenAdsShow = false
                        NextScreen(mintent)
                    }

                    override fun OnError() {
                        BaseSharedPreferences(this@SubToNextScreen).mOpenAdsShow = false
                        NextScreen(mintent)
                    }
                })
            }else{
                ActivityBuilder().Show_AppOpenAd(this,BaseSharedPreferences(this).mIS_SUBSCRIBED!!,Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT,object : OnAppOpenShowAds {
                    override fun OnDismissAds() {
                        Load_AppOpenAd(this@SubToNextScreen,false,APP_OPEN_AD_ORIENTATION_PORTRAIT)
                        BaseSharedPreferences(this@SubToNextScreen).mOpenAdsShow = false
                        NextScreen(mintent)
                    }
                    override fun OnError() {
                        BaseSharedPreferences(this@SubToNextScreen).mOpenAdsShow = false
                        NextScreen(mintent)
                    }
                })
            }

        }
         fun mSubscriptionFlow(context: Context,mclass:String,isSplashShowAds: IsSplashShowAds) {
            with(BaseSharedPreferences(context)){
                if (!mFirstTimePremium!!) {
                    logD(TAG, "->First Day Subscription Screen Open")
                    mFirstTimePremium = true
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val currentDate = sdf.format(Date())
                    mFirstDate = currentDate
                    mOneDay = true
                    mFirstTimeApp = 0

                    context.startActivity(
                        Intent(context, SubscriptionBackgroundActivity::class.java)
                            .putExtra("AppOpen", "SplashScreen")
                            .putExtra("mNextActivityIntent", mclass)
                    )
                    (context as Activity).finish()
                }
                else {
                    val sdf = SimpleDateFormat("dd/MM/yyyy")
                    val currentDate = sdf.format(Date())
                    val day = sdf.parse(currentDate)
                    if (sdf.parse(mFirstDate!!)!!.before(day)) {
                        if (!mSecondTimePremium!!) {
                            logD(TAG, "->Second Day Subscription Screen Open")
                            mOneDay = false
                            mTwoDay = true
                            mSecondTime = true
                            mFirstTimeApp = 0
                            mSecondTimePremium = true
                            mOpenAdsload=true

                            context.startActivity(
                                Intent(
                                    context,
                                    SubscriptionBackgroundActivity::class.java
                                )
                                    .putExtra("AppOpen", "SplashScreen")
                                    .putExtra("mNextActivityIntent", mclass)
                            )
                            (context as Activity).finish()
                        }
                        else {
                            //Ads Showing
                            mTwoDay=false
                            mOneDay=false
                            logD(TAG, "->Second Day Ads Showing")
                            mOpenAdsShow = true
                            val intent=Intent(context, Class.forName(mclass))
                            context.SubToNextScreen(intent,isSplashShowAds)
                        }
                    }
                    else if (mOneDay!! && sdf.parse(mFirstDate!!)!! == day) {
                        if (mFirstTimePremium!! && !mSecondTime!!) {
                            logD(TAG, "->First Day Second Time Subscription Screen Open")
                            mOneDay = true
                            mSecondTime = true
                            mOpenAdsload=true

                            context.startActivity(
                                Intent(
                                    context,
                                    SubscriptionBackgroundActivity::class.java
                                )
                                    .putExtra("AppOpen", "SplashScreen")
                                    .putExtra("mNextActivityIntent", mclass)
                            )
                            (context as Activity).finish()
                        }
                        else {
                            //Ads Showing
                            logD(TAG, "->First Day Three Time Ads Showing")
                            mOpenAdsShow = true
                            val intent=Intent(context, Class.forName(mclass))
                            context.SubToNextScreen(intent,isSplashShowAds)

                        }

                    }
                    else {
                        //Ads Showing
                        logD(TAG, "->Three Day Ads Showing")
                        mOpenAdsShow = true
                        mTwoDay=false
                        mOneDay=false
                        val intent=Intent(context, Class.forName(mclass))
                        context.SubToNextScreen(intent,isSplashShowAds)
                    }
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
        fun Show_CustomAdaptiveBanner(context: Context,is_SUBSCRIBED: Boolean, view:ViewGroup,isShowBannerAds: IsShowBannerAds,marginWidth:Int,maxHeight:Int,onCustomBanner: OnCustomBanner){
            with(context){
                if (isOnline && !is_SUBSCRIBED){
                    if (isShowAdmobAds && AD_Banner!=null && AD_Banner!=Noads){
                        loadAdaptiveCustiomBanner(view,isShowBannerAds,marginWidth,maxHeight,onCustomBanner)
                    }else {
                        if(FB_Banner==null && FB_Banner==Noads){
                            return
                        }
                    loadFBCustomAdaptiveBanner(view,isShowBannerAds,onCustomBanner)
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
                  if (Constants.isTestMode!!) {

                     loadAppOpenAd(is_SUBSCRIBED, "ca-app-pub-3940256099942544/3419835294", appOpenAd)
                  } else{
                      loadAppOpenAd(is_SUBSCRIBED, AD_AppOpen!!, appOpenAd)
                  }
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