package com.demo.adsmanage.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.adsmanage.Activity.SubscriptionActivity
import com.demo.adsmanage.Commen.Constants.BaseSubscriptionBackground
import com.demo.adsmanage.Commen.Constants.PREMIUM_SIX_SKU
import com.demo.adsmanage.Commen.Constants.SUBButtonTextColor
import com.demo.adsmanage.Commen.Constants.mAppIcon
import com.demo.adsmanage.Commen.Constants.mAppName
import com.demo.adsmanage.Commen.Constants.mAppNameColor
import com.demo.adsmanage.Commen.Constants.mBasic_Line_Icon
import com.demo.adsmanage.Commen.Constants.mClose_Icon
import com.demo.adsmanage.Commen.Constants.mIsRevenuCat
import com.demo.adsmanage.Commen.Constants.mMainLineColor
import com.demo.adsmanage.Commen.Constants.mPremiumLine
import com.demo.adsmanage.Commen.Constants.mPremium_Button_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_True_Icon
import com.demo.adsmanage.Commen.Constants.mSmallSubLineColor
import com.demo.adsmanage.Commen.Constants.mSubLineColor
import com.demo.adsmanage.Commen.Constants.packagerenlist
import com.demo.adsmanage.SubscriptionBaseClass.manager.PreferencesKeys
import com.demo.adsmanage.SubscriptionBaseClass.manager.SubscriptionManager
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.adsmanage.databinding.ActivitySubscriptionBackgroundBinding
import com.demo.adsmanage.helper.IconPosition
import com.demo.adsmanage.helper.click
import com.demo.adsmanage.helper.getSubTrial
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.demo.adsmanage.helper.setCloseIconPosition
import com.demo.adsmanage.helper.showToast
import com.demo.adsmanage.mbilling.ProductPurchaseHelper.getProductInfo
import com.google.firebase.analytics.FirebaseAnalytics

import org.jetbrains.anko.textColor

class SubscriptionBackgroundActivityViewModel(
    var binding: ActivitySubscriptionBackgroundBinding,
    var  mActivity: AppCompatActivity,
    var liveDataPeriod: MutableLiveData<HashMap<String, String>>,
    var liveDataPrice: MutableLiveData<HashMap<String, String>>,
    var subscriptionManager: SubscriptionManager,
    var isSelecterdPlan: IsSelecterdPlan
) : ViewModel() {
    var mFirebaseAnalytics: FirebaseAnalytics? = null
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three","_four","_five","_six","_seven","_eight")
    val TAG =mActivity.javaClass.simpleName
    interface IsSelecterdPlan {
        fun monMonthPlan()
        fun monYearPlan()
        fun monBackPress()
    }
    init {
        onMain()
    }
    fun onMain(){
//        InterstitialAds().loadInterstitialAd(mActivity)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mActivity)
        setUI()
        setSubScriptionUI()
        setLineView()
        initListener()
    }
//    val itemJeggings = Bundle().apply {
//        putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123")
//        putString(FirebaseAnalytics.Param.ITEM_VARIANT, "Monthly")
//        putDouble(FirebaseAnalytics.Param.VALUE, (8*80).toDouble())
//    }
    val itemJeggings = Bundle().apply {
        putString(FirebaseAnalytics.Param.ITEM_ID, "SKU_123")
        putString(FirebaseAnalytics.Param.ITEM_NAME, "jeggings")
        putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "pants")
        putString(FirebaseAnalytics.Param.ITEM_VARIANT, "black")
        putString(FirebaseAnalytics.Param.ITEM_BRAND, "Google")
        putDouble(FirebaseAnalytics.Param.PRICE, 9.99)
    }
    val itemJeggingsCart = Bundle(itemJeggings).apply {
        putLong(FirebaseAnalytics.Param.QUANTITY, 1)
    }
    @SuppressLint("InvalidAnalyticsName")
    fun initListener() {
        binding.ivClose.click {
            isSelecterdPlan.monBackPress()
        }
        binding.mCLUnlockLayout.click {
            val productBundle =  Bundle()
//            productBundle.putString("product_id", "sku1234")
//            productBundle.putString("product_name", "Monthly")
//            productBundle.putDouble("price", (8*80).toDouble())
//            productBundle.putString("product_name", "sku1234");
//            productBundle.putString("purchase_type", "Monthly");
//            val bundle = Bundle()
//            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "sku1234")
//            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "In App Purchase")
//            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, "Monthly")
//            bundle.putString(FirebaseAnalytics.Param.CURRENCY, "USD")
            val bundle = Bundle()
            bundle.putString("IsCheckPurchaseEvent", "IsVerent")
            mFirebaseAnalytics!!.logEvent("IsCheckPurchaseEvent", bundle)

//            IsCheckPurchaseEvent
//            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.PURCHASE) {
//                param(FirebaseAnalytics.Param.TRANSACTION_ID, "T12345")
//                param(FirebaseAnalytics.Param.AFFILIATION, "Google Store")
//                param(FirebaseAnalytics.Param.CURRENCY, "USD")
//                param(FirebaseAnalytics.Param.VALUE, 14.98)
//                param(FirebaseAnalytics.Param.TAX, 2.58)`
//                param(FirebaseAnalytics.Param.SHIPPING, 5.34)
//                param(FirebaseAnalytics.Param.COUPON, "SUMMER_FUN")
//                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggingsCart))
//            }
//            mFirebaseAnalytics!!.logEvent("purchase_event_check") {
//                param(FirebaseAnalytics.Param.ITEMS, arrayOf(productBundle))
//            }
//            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.PURCHASE) {
//                param(FirebaseAnalytics.Param.ITEMS, arrayOf(itemJeggings))
//            }
//            mFirebaseAnalytics!!.logEvent("purchase_event_check", productBundle)
            mActivity.showToast("Click.", android.widget.Toast.LENGTH_SHORT)
            if (mActivity.isOnline) {
                isSelecterdPlan.monMonthPlan()

            } else {
                mActivity.showToast("Please check internet connection.", android.widget.Toast.LENGTH_SHORT)
            }
        }
        binding.txtTryLimited.click {
            if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                val mintent = Intent(mActivity, SubscriptionActivity::class.java)
                mintent.putExtra("AppOpen", mActivity.intent.getStringExtra("AppOpen"))
                mActivity.startActivity(mintent)
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                isSelecterdPlan.monBackPress()
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                isSelecterdPlan.monBackPress()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun setSubScriptionUI(){
        with(binding){
            if (isPiePlus()) {
                mActivity.setCloseIconPosition(
                    fParentLayout = mainleyouut2, // Parent Constraint Layout
                    fCloseIcon = ivClose, // Image View
                    fIconPosition = IconPosition.RIGHT // IconPosition Left or Right
                )
            }
            txtAppname.text=mAppName
            txtAppname.textColor= mActivity.resources.getColor(mAppNameColor!!)
            txtPremium.textColor= mActivity.resources.getColor(mMainLineColor!!)
            txtBasic.textColor= mActivity.resources.getColor(mMainLineColor!!)
            textPrice.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtMessage1.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtTryLimited.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtTerms.textColor= mActivity.resources.getColor(mSmallSubLineColor!!)
            txtUnlockKriadl.textColor=mActivity.resources.getColor(SUBButtonTextColor!!)
            imgBg.background=BaseSubscriptionBackground
                if (mIsRevenuCat!!){
                Handler().postDelayed(Runnable {
                    packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                        val size = it1.length
                        val period = it1.substring(1, size - 1)
                        val str = it1.substring(size - 1, size)
                        Log.d("TAG", "getSubTrial: ${size} $period - $str")
                        textPrice.text = "${packagerenlist?.get(0)?.price}/Month after ${
                            packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                getSubTrial(it1)
                            }
                        } of FREE trial."
                    }
                }, 200)
            }else{
                Handler().postDelayed({
                    liveDataPeriod.observe(mActivity) { trial ->
                        liveDataPrice.observe(mActivity) { price ->


                            PREMIUM_SIX_SKU.getProductInfo?.let { month ->
                                logD(TAG," onCreate: liveDataPeriod->$trial\nliveDataPrice->$price\n trial->${subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")}---${month.freeTrialPeriod}")
                                if (month.freeTrialPeriod.equals("Not Found", true) || month.freeTrialPeriod.isEmpty() ) {
                                    textPrice.text = "${
                                        price[PREMIUM_SIX_SKU]?.replace(
                                            ".00",
                                            ""
                                        )
                                    }/Month."
                                    txtUnlockKriadl.text = "Continue"
                                } else {

                                    textPrice.text="${ price[PREMIUM_SIX_SKU]?.replace(
                                        ".00",
                                        ""
                                    )}/Month after ${
                                        getSubTrial(
                                            subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")
                                        )
                                    } FREE trial"
                                    txtUnlockKriadl.text = "start free trial"
                                }
                            }
                        }
                    }
                }, 200)
            }
        }
    }
    fun setLineView(){
        with(binding){
            ivClose.setImageDrawable(mClose_Icon)
            imgAppIcon.setImageDrawable(mAppIcon)
            mCLUnlockLayout.setBackgroundDrawable(mPremium_Button_Icon)
            for (i in  0..7){
                logD("isforrlooopcheck","----$i--${mPremiumLine!!.size}--${idname[i]}--${(mPremiumLine!!.size==(i+1))}")
                if (mPremiumLine!!.size<=i){
                    val id_name="txt${idname[i]}"
                    val redId=mActivity.resources.getIdentifier(id_name,"id",mActivity.packageName)
                    val txt: TextView = mActivity.findViewById(redId)
                    txt.visibility=View.GONE

                    val id_name1="img_true${idname[i]}"
                    val redId1=mActivity.resources.getIdentifier(id_name1,"id",mActivity.packageName)
                    val img_true: ImageView = mActivity.findViewById(redId1)
                    img_true.visibility=View.GONE

                    val id_name2="img_Pright${idname[i]}"
                    val redId2=mActivity.resources.getIdentifier(id_name2,"id",mActivity.packageName)
                    val img_Pright: ImageView = mActivity.findViewById(redId2)
                    img_Pright.visibility=View.GONE

                    val id_name3="img_Bright${idname[i]}"
                    val redId3=mActivity.resources.getIdentifier(id_name3,"id",mActivity.packageName)
                    val img_Bright: ImageView = mActivity.findViewById(redId3)
                    img_Bright.visibility=View.GONE
                }else{
                    val id_name="txt${idname[i]}"
                    val redId=mActivity.resources.getIdentifier(id_name,"id",mActivity.packageName)
                    val txt: TextView = mActivity.findViewById(redId)
                    txt.visibility=View.VISIBLE
                    txt.text= mPremiumLine!![i].mLine
                    txt.textColor= mActivity.resources.getColor(mPremiumLine!![i].mLineColor)

                    val id_name1="img_true${idname[i]}"
                    val redId1=mActivity.resources.getIdentifier(id_name1,"id",mActivity.packageName)
                    val img_true: ImageView = mActivity.findViewById(redId1)
                    img_true.visibility=View.VISIBLE
                    img_true.setImageDrawable(mPremiumLine!![i].mIconLine)

                    val id_name2="img_Pright${idname[i]}"
                    val redId2=mActivity.resources.getIdentifier(id_name2,"id",mActivity.packageName)
                    val img_Pright: ImageView = mActivity.findViewById(redId2)
                    img_Pright.visibility=View.VISIBLE
                    img_Pright.setImageDrawable(mPremium_True_Icon)

                    val id_name3="img_Bright${idname[i]}"
                    val redId3=mActivity.resources.getIdentifier(id_name3,"id",mActivity.packageName)
                    val img_Bright: ImageView = mActivity.findViewById(redId3)
                    img_Bright.visibility=View.VISIBLE
                    if (mPremiumLine!![i].mLineRight){
                        img_Bright.setImageDrawable(mBasic_Line_Icon)
                    }else{
                        img_Bright.setImageDrawable(mPremium_True_Icon)
                    }
                }

            }
        }
    }
    fun setUI(){
        with(binding){
            if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                if (BaseSharedPreferences(mActivity).mIS_SUBSCRIBED!!) {
                    mActivity.onBackPressed()
//                    super.onBackPressed()
                }
            }
            if (mActivity.intent.getStringExtra("AppOpen").equals("SplashScreen")) {
                if (BaseSharedPreferences(mActivity).mSecondTime!!) {
                    txtTryLimited.visibility = View.GONE

                } else {
                    txtTryLimited.visibility = View.VISIBLE
                    txtTryLimited.text = "OR TRY LIMITED VERSION"
                }
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("SettingsActivity")) {
                txtTryLimited.visibility = View.VISIBLE
                txtTryLimited.text = "Click here for more plans"
            } else if (mActivity.intent.getStringExtra("AppOpen").equals("BaseActivity")) {
                txtTryLimited.visibility = View.GONE
            }
        }
    }


}