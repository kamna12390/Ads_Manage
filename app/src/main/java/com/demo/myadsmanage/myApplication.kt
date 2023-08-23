package com.demo.myadsmanage

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.demo.adsmanage.AdsManage
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.Commen.Constants.IsOutAppPermission
import com.demo.adsmanage.Commen.Constants.isAdsClicking
import com.demo.adsmanage.Commen.Constants.isAdsShowing
import com.demo.adsmanage.InterFace.OnAppOpenShowAds
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.adsmanage.viewmodel.AppSubscription
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds

class myApplication : AppSubscription(), LifecycleObserver,
    Application.ActivityLifecycleCallbacks {
    var currentActivity: Activity? = null
    private var mPackagerenList = arrayListOf(
        Constants.PackagesRen(
            originalPrice = "₹610.00",
            freeTrialPeriod = "P1W",
            title = "Image Crop - Monthly PRO (Photo Crop: Cut, Convert, Trim)",
            price = "₹610.00",
            description = "",
            subscriptionPeriod = "P1M",
            sku = "subscribe_monthly_imagecrop_799"
        ),
        Constants.PackagesRen(
            originalPrice = "₹3,100.00",
            freeTrialPeriod = "P1W",
            title = "Image Crop - Monthly PRO (Photo Crop: Cut, Convert, Trim)",
            price = "₹3,100.00",
            description = "",
            subscriptionPeriod = "P1Y",
            sku = "subscribe_yearly_imagecrop_3999"
        )
    )

    override fun onCreate() {
        super.onCreate()
        val mBasePremiumLine = arrayListOf(
            Constants.LineWithIconModel(
                "Restore Messages",
                false,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "Deleted Media Viewer",
                false,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "VIP Customer Support",
                true,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "Remove Ads",
                true,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            )
        )
        val mMainPremiumLine = arrayListOf(
            Constants.LineWithIconModel(
                "Restore Messages",
                false,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "Deleted Media Viewer",
                false,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "VIP Customer Support",
                true,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            ),
            Constants.LineWithIconModel(
                "Remove Ads",
                true,
                this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher),
                R.color.white
            )
        )
        AdsManage.ActivityBuilder()
            .ApplicationCall(this)
            .setAdmobAdsID("ca-app-pub-3940256099942544/3419835294","ca-app-pub-3940256099942544/9214589741","ca-app-pub-3940256099942544/1033173712","ca-app-pub-3940256099942544/2247696110","ca-app-pub-3940256099942544/5224354917")
            .setFBAdsID("YOUR_PLACEMENT_ID","YOUR_PLACEMENT_ID","YOUR_PLACEMENT_ID","YOUR_PLACEMENT_ID")
            .setIsSubscription(true)
            .setBASIC_SKU("subscribe_monthly_")
            .setPREMIUM_SKU("subscribe_yearly_")
            .setPREMIUM_SIX_SKU("subscribe_monthly_")
            .setIsRevenuCat(false)
            .setRevenuCatPurchase_ID("goog_IvcEUSwmDPbXkcYQpLvPKAhtbfO")
            .setRevenuCatDefaultList(mPackagerenList)
            .setPrivacyPolicyURL("https://imagecropnwallpaperchanger.blogspot.com/2023/07/privacy-policy.html")
            .setBackgroundSubscreenLine(mBasePremiumLine)
            .setSubScreenOfLine(mMainPremiumLine)
            .setAppName(this.resources.getString(R.string.app_name), com.demo.adsmanage.R.color.mcolorPrimary)
            .setBackgroundSubscreenLineColor(R.color.black)
            .setPriceTextColor(R.color.white)
            .setSubLineColor(com.demo.adsmanage.R.color.dark_gray)
            .setNavigationBarColor(com.demo.adsmanage.R.color.white)
            .setSmallSubLineColor(com.demo.adsmanage.R.color.light_gray_color)
            .setSUBButtonTextColor(R.color.black)
            .setAppIcon(this.resources.getDrawable(com.demo.adsmanage.R.drawable.ic_launcher))
            .setPremium_True_Icon(this.resources.getDrawable(R.drawable.ic_true_icon))
            .setBasic_Line_Icon(this.resources.getDrawable(R.drawable.ic_line_lock))
            .setBaseSubscriptionBackground(this.resources.getDrawable(R.drawable.ic_basesub))
            .setSubscriptionBackground(this.resources.getDrawable(R.drawable.ic_basesub))
            .setPriceBackground(this.resources.getDrawable(R.drawable.ic_price_bg))
            .setClose_Icon(this.resources.getDrawable(R.drawable.close_icon))
            .setPremium_CardSelected_Icon(this.resources.getDrawable(R.drawable.ic_pro_selected))
            .setPremium_Cardunselected_Icon(this.resources.getDrawable(R.drawable.ic_pro_selection))
            .setPremium_Button_Icon(this.resources.getDrawable(R.drawable.bg_sub_btn_))
            .Subcall(this)




        AudienceNetworkAds.initialize(this);
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)


        AdSettings.addTestDevice("6346fcdc-e401-4861-b6d7-784db5e16e62")
        AdSettings.addTestDevice("671f6024-072e-40db-8d06-88b486f9b29a")

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onappBackground() {
        AdsManage.ActivityBuilder().let {
            if (!isAdsClicking) {
                it.setActivityOpen(true, this)
            }
        }

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cn: ComponentName? = am.getRunningTasks(1)[0].topActivity
        if (!BaseSharedPreferences(this).mIS_SUBSCRIBED!!) {
            if (!isAdsClicking && !IsOutAppPermission && !isAdsShowing
                && (cn?.className != "com.demo.myadsmanage.Activity.SplachActivity")
                && (cn?.className != "com.demo.adsmanage.Activity.SubscriptionBackgroundActivity")
                && (cn?.className != "com.demo.adsmanage.Activity.SubscriptionActivity")
            ) {
                currentActivity?.let {
                    AdsManage.ActivityBuilder().Show_AppOpenAd(it,false,Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT,object : OnAppOpenShowAds {
                        override fun OnDismissAds() {
                            AdsManage.ActivityBuilder().Load_AppOpenAd(it,
                                BaseSharedPreferences(it).mIS_SUBSCRIBED!!,Constants.APP_OPEN_AD_ORIENTATION_PORTRAIT)
                        }

                        override fun OnError() {

                        }
                    })
                }
            }

        }
        AdsManage.ActivityBuilder().let {
            isAdsClicking = false
            it.setActivityOpen(false, this)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {
        if (!isAdsShowing) {

            currentActivity = activity
        }
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}