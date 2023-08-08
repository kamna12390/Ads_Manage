package com.demo.adsmanage.Commen
import android.content.SharedPreferences
import android.graphics.drawable.Drawable

object Constants {
    var PREMIUM_SIX_SKU = ""
    var BASIC_SKU = ""
    var PREMIUM_SKU = ""
    var Purchase_ID = ""


    var mInterstitialAdlist:Any?=null
    var mNativeAdlist:Any?=null
    var mRewardedAds:Any?=null
    var mAppOpenAds:Any?=null
    var mAppOpenAds_LANDSCAPE:Any?=null

    var isInter_RequestSend: Boolean=false
    var isRewarde_RequestSend: Boolean=false
    var isAppOpen_RequestSend: Boolean=false
    var isAppOpen_RequestSend_LANDSCAPE: Boolean=false

    var isAdsShowing: Boolean=false
    var isTestMode: Boolean=false
    var isDebugMode: Boolean=true
    var isAdsClicking: Boolean=false
    var isActivitychange: Boolean? = false
    var IsOutAppPermission:Boolean=false
    var isShowAdmobAds: Boolean=true
    var isHomeNativeShow: Boolean=true
    var isSettingNativeShow: Boolean=true
    var isCreationNativeShow: Boolean=true
    var is_ProgressShow: Boolean=false
    var is_BackAdsShow: Boolean=false
    var mIsRevenuCat: Boolean? = false

    var is_ABTest: Int=0

    var mPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

     val APP_OPEN_AD_ORIENTATION_PORTRAIT = 1
     val APP_OPEN_AD_ORIENTATION_LANDSCAPE = 2

    var mInterstitialAds_clickCount=0

    var mHEIGHT:Int?=0
    var mWIDTH:Int?=0

    var Noads="NoAds"
    var mPrivacyPolicyURL:String?=""


    var mAppIcon: Drawable?=null
    var mPremium_True_Icon:Drawable?=null
    var mBasic_Line_Icon:Drawable?=null
    var BaseSubscriptionBackground:Drawable?=null
    var SubscriptionBackground:Drawable?=null
    var mPriceBackground:Drawable?=null
    var mClose_Icon:Drawable?=null
    var mPremium_Button_Icon:Drawable?=null
    var mPremium_CardSelected_Icon:Drawable?=null
    var mPremium_Cardunselected_Icon:Drawable?=null


    var mAppName:String?=null
    var mAppNameColor:Int?=null
    var mMainLineColor:Int?=null
    var mSubLineColor:Int?=null
    var mSmallSubLineColor:Int?=null
    var mPriceLineColor:Int?=null
    var SUBButtonTextColor:Int?=null
    var NavigationBarColor:Int?=null

    var mPremiumLine: ArrayList<LineWithIconModel>?=null
    var mPremiumScreenLine: ArrayList<LineWithIconModel>?=null
    var packagerenlist: ArrayList<PackagesRen>? = null

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