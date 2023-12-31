package com.demo.adsmanage.mbilling.repository

import android.content.Context
import android.util.Log

class AdsManagerRepo(context: Context) {
    @Suppress("PrivatePropertyName")
    private val TAG: String = javaClass.simpleName

    // SP to be save & retrieve
    private val isNeedToShow = "isNeedToShow"
    private val isSubscribe = "isSubscribe"
    private val sp: SharedPreferences = SharedPreferences(context)

    fun onProductPurchased() {
        Log.e(TAG,"onProductPurchased")
        sp.save(isNeedToShow, true)
    }
    fun onProductExpired() {
        Log.e(TAG,"onProductExpired")
        sp.save(isNeedToShow, false)
    }

    fun onProductSubscribed() {
        Log.e(TAG,"onProductSubscribed")
        sp.save(isSubscribe, true)
    }
    fun onSubscribeExpired() {
        Log.e(TAG,"onSubscribeExpired")
        sp.save(isSubscribe, false)
    }

    fun isNeedToShowAds(): Boolean {
        val isProductPurchased = sp.read(isNeedToShow, false)
        val isSubscribe = sp.read(isSubscribe, false)

        Log.e(TAG, "isNeedToShowAds:isProductPurchased-$isProductPurchased")
        Log.e(TAG, "isNeedToShowAds:isSubscribe-$isSubscribe")
        return !(isProductPurchased || isSubscribe)
    }


    /**
     *   SharedPreferences helper class
     */
    private inner class SharedPreferences//  Default constructor
        (private val context: Context) {
        private val myPreferences = "ads_pref"

        //  Save boolean value
        fun save(key: String, value: Boolean) {
            val editor = context.getSharedPreferences(myPreferences, 0).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        //  Read Boolean value
        fun read(key: String, defValue: Boolean): Boolean {
            return context.getSharedPreferences(myPreferences, 0).getBoolean(key, defValue)
        }
    }
}