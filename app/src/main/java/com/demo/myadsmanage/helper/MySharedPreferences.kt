package com.demo.myadsmanage.helper

import com.demo.myadsmanage.myApplication.Companion.editor
import com.demo.myadsmanage.myApplication.Companion.mPreferences
import com.google.gson.Gson

object MySharedPreferences {

     var AD_Interstitial:List<String>
        get(){
            val gson = Gson()
            val jsonText = mPreferences!!.getString("ad_Interstitial", "")
            val list = gson.fromJson(jsonText, Array<String>::class.java)
          return list.toList()
        }
        set(string) {
            val gson = Gson()
            val jsonText = gson.toJson(string)
            editor!!.putString("ad_Interstitial", jsonText!!)
            editor!!.commit()
        }
    var AD_NativeAds:List<String>
        get() {
            val gson = Gson()
            val jsonText = mPreferences!!.getString("ad_NativeAds", "")
            val list = gson.fromJson(jsonText, Array<String>::class.java)
            return list.toList()
        }
        set(string) {
            val gson = Gson()
            val jsonText = gson.toJson(string)
            editor!!.putString("ad_NativeAds", jsonText!!)
            editor!!.commit()
        }
      var AD_Banner:String?
        get() = mPreferences!!.getString("ad_Banner", "")
        set(string) {
            editor!!.putString("ad_Banner", string!!)
            editor!!.commit()
        }
    var AD_AppOpen:String?
        get() = mPreferences!!.getString("ad_AppOpen", "")
        set(string) {
            editor!!.putString("ad_AppOpen", string!!)
            editor!!.commit()
        }
    var AD_RewardedAds:String?
        get() = mPreferences!!.getString("ad_RewardedAds", "")
        set(string) {
            editor!!.putString("ad_RewardedAds", string!!)
            editor!!.commit()
        }
    var FB_Interstitial:String?
        get() = mPreferences!!.getString("fb_Interstitial", "")
        set(string) {
            editor!!.putString("fb_Interstitial", string!!)
            editor!!.commit()
        }
    var FB_Banner:String?
        get() = mPreferences!!.getString("fb_Banner", "")
        set(string) {
            editor!!.putString("fb_Banner", string!!)
            editor!!.commit()
        }
    var FB_AppOpen:String?
        get() = mPreferences!!.getString("fb_AppOpen", "")
        set(string) {
            editor!!.putString("fb_AppOpen", string!!)
            editor!!.commit()
        }
    var FB_NativeAds:String?
        get() = mPreferences!!.getString("fb_NativeAds", "")
        set(string) {
            editor!!.putString("fb_NativeAds", string!!)
            editor!!.commit()
        }
    var FB_RewardedAds:String?
        get() = mPreferences!!.getString("fb_RewardedAds", "")
        set(string) {
            editor!!.putString("fb_RewardedAds", string!!)
            editor!!.commit()
        }
    var Interstitial_CountShow:Int?
        get() = mPreferences!!.getInt("Interstitial_Click_CountShow", 1)
        set(number) {
            editor!!.putInt("Interstitial_Click_CountShow", number!!)
            editor!!.commit()
        }
}