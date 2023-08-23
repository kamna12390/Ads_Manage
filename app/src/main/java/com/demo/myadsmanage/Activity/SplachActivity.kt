package com.demo.myadsmanage.Activity

import android.os.Bundle
import android.util.Log
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.demo.adsmanage.AdsManage
import com.demo.adsmanage.InterFace.IsSplashShowAds
import com.demo.adsmanage.SubscriptionBaseClass.SubSplashBaseActivity
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.adsmanage.mbilling.ProductPurchaseHelper
import com.demo.adsmanage.mbilling.repository.AdsManagerRepo
import com.demo.myadsmanage.R

class SplachActivity :  SubSplashBaseActivity(), ProductPurchaseHelper.ProductPurchaseListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        ProductPurchaseHelper.initBillingClient(this, this)
        AdsManage.ActivityBuilder()
            .Splash_Init(this,"AdsManageTesting","com.demo.myadsmanage.Activity.MainActivity",2000,IsSplashShowAds.IsAppOpenAdsORIENTATION_PORTRAIT)
    }

    override fun registerPurchases(
        isSubscribe: Boolean,
        paymentState: Int,
        sku: String,
        orderId: String
    ) {
        Log.d("TAG", "registerPurchases: --=$isSubscribe")
        BaseSharedPreferences(this).mIS_SUBSCRIBED=isSubscribe
    }

    override fun onPurchasedSuccess(purchase: Purchase) {

    }

    override fun onProductAlreadyOwn() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        ProductPurchaseHelper.initProductsKeys(this) {
            val sub = !AdsManagerRepo(this).isNeedToShowAds()
            BaseSharedPreferences(this).mIS_SUBSCRIBED=sub
        }
    }

    override fun onBillingKeyNotFound(productId: String) {

    }
}