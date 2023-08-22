package com.demo.adsmanage.SubscriptionBaseClass

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.SkuDetails
import com.demo.adsmanage.Commen.Constants.BASIC_SKU
import com.demo.adsmanage.Commen.Constants.PREMIUM_SIX_SKU
import com.demo.adsmanage.Commen.Constants.PREMIUM_SKU
import com.demo.adsmanage.mbilling.BillingClientLifecycle
import com.demo.adsmanage.SubscriptionBaseClass.manager.PreferencesKeys
import com.demo.adsmanage.SubscriptionBaseClass.manager.SubscriptionManager
import com.demo.adsmanage.viewmodel.AppSubscription
import com.demo.adsmanage.mbilling.BillingViewModel

abstract class BaseSubscriptionActivity : AppCompatActivity() {

    private val mBillingViewModel: BillingViewModel by viewModels<BillingViewModel>()
    // val mSubscriptionViewModel: SubscriptionStatusViewModel by viewModels<SubscriptionStatusViewModel>()

    private lateinit var billingClientLifecycle: BillingClientLifecycle

    private var mPriceMap: HashMap<String, String> = HashMap()
    private var mPriceMapMicro: HashMap<String, Long> = HashMap()
    private var mTrialPeriod: HashMap<String, String> = HashMap()

    protected var liveDataPrice = MutableLiveData<HashMap<String, String>>()
    protected var liveDataPriceMicro = MutableLiveData<HashMap<String, Long>>()
    protected var liveDataPeriod = MutableLiveData<HashMap<String, String>>()
    protected var currencyCode = MutableLiveData<String>()

    lateinit var subscriptionManager: SubscriptionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscriptionManager = SubscriptionManager(this)

        mPriceMap[PREMIUM_SIX_SKU] = subscriptionManager.getString(PreferencesKeys.SIXMonth_PRICE,"₹100.00")
        mPriceMap[BASIC_SKU] = subscriptionManager.getString(PreferencesKeys.MONTH_PRICE,"₹249.00")
        mPriceMap[PREMIUM_SKU] = subscriptionManager.getString(PreferencesKeys.YEAR_PRICE,"₹1,999.00")

        mPriceMapMicro[PREMIUM_SIX_SKU] = subscriptionManager.getLong(PreferencesKeys.YSIX_PRICE_MICRO,100000000)
        mPriceMapMicro[BASIC_SKU] = subscriptionManager.getLong(PreferencesKeys.MONTH_PRICE_MICRO,249000000)
        mPriceMapMicro[PREMIUM_SKU] = subscriptionManager.getLong(PreferencesKeys.YEAR_PRICE_MICRO,1999000000)
//        Log.d(TAG, "onCreate:111 ${BASIC_SKU}--${subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")}")
        mTrialPeriod[BASIC_SKU] = subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")
        mTrialPeriod[PREMIUM_SKU] = subscriptionManager.getString(PreferencesKeys.YEAR_TRIAL_PERIOD,"")
        mTrialPeriod[PREMIUM_SIX_SKU] = subscriptionManager.getString(PreferencesKeys.SIX_TRIAL_PERIOD,"")
//        mTrialPeriod[PREMIUM_SIX_SKU] = subscriptionManager.getString(PreferencesKeys.SIX_TRIAL_PERIOD,"P1D")
        currencyCode.postValue(subscriptionManager.getString(PreferencesKeys.CURRENCY_CODE,"INR"))
        liveDataPrice.postValue(mPriceMap)
        liveDataPriceMicro.postValue(mPriceMapMicro)
        liveDataPeriod.postValue(mTrialPeriod)

        /*subscriptionManager.values.asLiveData().observe(this, Observer {
            it[PreferencesKeys.MONTH_PRICE]?.let {
                mPriceMap[BASIC_SKU] = it
            } ?: kotlin.run {
                mPriceMap[BASIC_SKU] = "₹250.00"
            }
            it[PreferencesKeys.YEAR_PRICE]?.let {
                mPriceMap[PREMIUM_SKU] = it
            } ?: kotlin.run {
                mPriceMap[PREMIUM_SKU] = "₹1,550.00"
            }

            it[PreferencesKeys.MONTH_TRIAL_PERIOD]?.let {
                mTrialPeriod[BASIC_SKU] = it
            } ?: kotlin.run {
                mTrialPeriod[BASIC_SKU] = "P3D"
            }
            it[PreferencesKeys.YEAR_TRIAL_PERIOD]?.let {
                mTrialPeriod[PREMIUM_SKU] = it
            } ?: kotlin.run {
                mTrialPeriod[PREMIUM_SKU] = "P3D"
            }

        })*/

        // Billing APIs are all handled in the this lifecycle observer.
        billingClientLifecycle = (application as AppSubscription).billingClientLifecycle
        lifecycle.addObserver(billingClientLifecycle)


        // Register purchases when they change.
        billingClientLifecycle.purchaseUpdateEvent.observe(this, Observer {
            registerPurchases(it)
        })

        // Launch the mbilling flow when the user clicks a button to buy something.
        mBillingViewModel.buyEvent.observe(this, Observer {
            it?.let {
                billingClientLifecycle.launchBillingFlow(this, it)
            }
        })

        mBillingViewModel.skusWithSkuDetails.observe(this, Observer {
            it.forEach {
                val sku: SkuDetails = it.value
                if (sku.sku == BASIC_SKU) {
                    Log.d(TAG, "onCreate:11 ${BASIC_SKU}--${sku.freeTrialPeriod}")
                    subscriptionManager.setMonthPrice(sku.price)
                    subscriptionManager.setMonthPrice(sku.priceAmountMicros)
                    subscriptionManager.setMonthTrialPeriod(sku.freeTrialPeriod)
//                    subscriptionManager.setMonthTrialPeriod("")
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                } else if (sku.sku == PREMIUM_SKU) {
//                    Log.d(TAG, "onCreate:22 ${PREMIUM_SKU}")
                    subscriptionManager.setYearPrice(sku.price)
                    subscriptionManager.setYearPrice(sku.priceAmountMicros)
                    subscriptionManager.setYearTrialPeriod(sku.freeTrialPeriod)
//                    subscriptionManager.setYearTrialPeriod("")
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                } else if (sku.sku == PREMIUM_SIX_SKU) {
//                    Log.d(TAG, "onCreate:33 ${PREMIUM_SIX_SKU}")
                    subscriptionManager.setSixMonthrPrice(sku.price)
                    subscriptionManager.setSixPrice(sku.priceAmountMicros)
                    subscriptionManager.setSixTrialPeriod(sku.freeTrialPeriod)
//                    subscriptionManager.setSixTrialPeriod("")
                    subscriptionManager.setCurrencyCode(sku.priceCurrencyCode)
                }
            }

        })


    }

    fun onMonthPlan() {
        mBillingViewModel.buyBasic()
    }

    fun onYearPlan() {
        mBillingViewModel.buyPremium()
    }
    fun onSixPlan() {
        mBillingViewModel.buySixPremium()
    }

    /**
     * Register SKUs and purchase tokens with the server.
     */
    private fun registerPurchases(purchaseList: List<Purchase>?) {
        purchaseList?.let {
            Log.d(TAG, "registerPurchases: ${purchaseList.size}")
            if (it.isNotEmpty()) {
                subscriptionManager.setSubscribe(true)
                onPurchases(it[0].orderId!!,it[0].skus[0])
            } else {
                subscriptionManager.setSubscribe(false)
            }
        } ?: kotlin.run {
            subscriptionManager.setSubscribe(false)
        }
    }

    private fun refreshData() {
        billingClientLifecycle.queryPurchases()
        //mSubscriptionViewModel.manualRefresh()
    }

    /**
     * Callback for Purchases
     */
    abstract fun onPurchases(orderId : String,str: String)

    companion object {
        private const val TAG = "SubscriptionActivity"
    }
}