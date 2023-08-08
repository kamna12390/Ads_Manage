package com.demo.myadsmanage.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.demo.adsmanage.AdsManage
import com.demo.adsmanage.InterFace.OnSplachAds
import com.demo.adsmanage.SubscriptionBaseClass.SubSplashBaseActivity
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.adsmanage.billing.ProductPurchaseHelper
import com.demo.adsmanage.billing.repository.AdsManagerRepo
import com.demo.myadsmanage.R

class SplachActivity :  SubSplashBaseActivity(), ProductPurchaseHelper.ProductPurchaseListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach)
        ProductPurchaseHelper.initBillingClient(this, this)
        AdsManage.ActivityBuilder().Splash_Init(this,"AdsManage","com.demo.myadsmanage.Activity.MainActivity")
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

    override fun onPurchasedSuccess(purchase: com.android.billingclient.api.Purchase) {

    }

    override fun onProductAlreadyOwn() {

    }

    override fun onBillingSetupFinished(billingResult: com.android.billingclient.api.BillingResult) {
        ProductPurchaseHelper.initProductsKeys(this) {
            val sub = !AdsManagerRepo(this).isNeedToShowAds()
            BaseSharedPreferences(this).mIS_SUBSCRIBED=sub
        }
    }

    override fun onBillingKeyNotFound(productId: String) {

    }
}