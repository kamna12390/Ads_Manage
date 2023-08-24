package com.demo.adsmanage.Commen
import android.content.SharedPreferences
import android.graphics.drawable.Drawable

//object Constants {
 object Constants {
    internal var PREMIUM_SIX_SKU = ""
    internal var BASIC_SKU = ""
    internal var PREMIUM_SKU = ""
    internal var Purchase_ID = ""


    internal var mInterstitialAdlist:Any?=null
    internal var mNativeAdlist:Any?=null
    internal var mRewardedAds:Any?=null
    internal var mAppOpenAds:Any?=null
    internal var mAppOpenAds_LANDSCAPE:Any?=null

    internal var isInter_RequestSend: Boolean=false
    internal var isRewarde_RequestSend: Boolean=false
    internal var isAppOpen_RequestSend: Boolean=false
    internal var isAppOpen_RequestSend_LANDSCAPE: Boolean=false

             var isAdsShowing: Boolean=false
   internal var isTestMode: Boolean=false
   internal var isDebugMode: Boolean=true
             var isAdsClicking: Boolean=false
   internal var isActivitychange: Boolean? = false
            var IsOutAppPermission:Boolean=false
   internal var isShowAdmobAds: Boolean=true
   internal var isHomeNativeShow: Boolean=true
   internal var isSettingNativeShow: Boolean=true
   internal var isCreationNativeShow: Boolean=true
   internal var is_ProgressShow: Boolean=false
             var is_BackAdsShow: Boolean=false
    internal var mIsRevenuCat: Boolean? = false
    internal var misSubscription: Boolean? = false

            var is_ABTest: Int=0

    internal var mPreferences: SharedPreferences? = null
    internal var editor: SharedPreferences.Editor? = null

    val APP_OPEN_AD_ORIENTATION_PORTRAIT = 1
    val APP_OPEN_AD_ORIENTATION_LANDSCAPE = 2

   internal  var mInterstitialAds_clickCount=0
   internal  var mSplashDelayTime:Long=0

    internal var mHEIGHT:Int?=0
    internal var mWIDTH:Int?=0

    internal var Noads="NoAds"
    internal var mPrivacyPolicyURL:String?=""


    internal var mAppIcon: Drawable?=null
    internal var mPremium_True_Icon:Drawable?=null
    internal var mBasic_Line_Icon:Drawable?=null
    internal var BaseSubscriptionBackground:Drawable?=null
    internal var SubscriptionBackground:Drawable?=null
    internal var mPriceBackground:Drawable?=null
    internal var mClose_Icon:Drawable?=null
    internal var mPremium_Button_Icon:Drawable?=null
    internal var mPremium_CardSelected_Icon:Drawable?=null
    internal var mPremium_Cardunselected_Icon:Drawable?=null


   internal var mAppName:String?=null
   internal var mAppNameColor:Int?=null
   internal var mMainLineColor:Int?=null
   internal var mSubLineColor:Int?=null
   internal var mSmallSubLineColor:Int?=null
   internal var mPriceLineColor:Int?=null
   internal var SUBButtonTextColor:Int?=null
   internal var NavigationBarColor:Int?=null
   internal var ProgressDialogBackgroundColor:Int?=null
   internal var ProgressDialoglayout:Int?=null

    internal var mPremiumLine: ArrayList<LineWithIconModel>?=null
    internal var mPremiumScreenLine: ArrayList<LineWithIconModel>?=null
    internal var packagerenlist: ArrayList<PackagesRen>? = null

    data class LineWithIconModel(
        val mLine:String,
        val mLineRight:Boolean,
        val mIconLine:Drawable,
        val mLineColor:Int
    )
     data class PackagesRen(
        val originalPrice: String,
        val freeTrialPeriod: String,
        val title: String,
        val price: String,
        val description: String,
        val subscriptionPeriod: String,
        val sku: String
    ) {
        override fun toString(): String {
            return "PackagesRen(originalPrice='$originalPrice', freeTrialPeriod='$freeTrialPeriod', title='$title', price='$price', description='$description', subscriptionPeriod='$subscriptionPeriod', sku='$sku')"
        }
    }


}