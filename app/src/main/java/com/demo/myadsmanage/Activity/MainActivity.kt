package com.demo.myadsmanage.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.adsmanage.Activity.SubscriptionBackgroundActivity
import com.demo.adsmanage.AdsManage
import com.demo.adsmanage.InterFace.IsShowBannerAds
import com.demo.adsmanage.InterFace.OnCustomBanner
import com.demo.adsmanage.InterFace.OnInterAdsShowAds
import com.demo.adsmanage.InterFace.OnRewardedShowAds
import com.demo.adsmanage.basemodule.BaseActivity
import com.demo.adsmanage.basemodule.BaseSharedPreferences
import com.demo.myadsmanage.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    var doubleBackToExitPressedOnce = false
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getActivityContext(): AppCompatActivity {
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.clTwo.visibility = View.GONE

        with(AdsManage.ActivityBuilder()) {
//            Show_AdaptiveBannerSize(this@MainActivity,false, binding.adsNativeThree,IsShowBannerAds.MEDIUM_RECTANGLE,object : OnCustomBanner {
//                override fun OnBannerAdsShow() {
//
//                }
//
//                override fun OnBannerAdsClick() {
//
//                }
//
//                override fun OnBannerAdsError() {
//
//                }
//
//            })
            Show_AdaptiveBannerCustomSize(this@MainActivity,false,binding.adsNativeOne,0,0,object : OnCustomBanner {
                override fun OnBannerAdsShow() {

                }

                override fun OnBannerAdsClick() {

                }

                override fun OnBannerAdsError() {

                }
            })
            Load_InterstitialAd(this@MainActivity, false)
        }

        Handler().postDelayed({ AdsManage.ActivityBuilder().Load_InterstitialAd(this@MainActivity,false) }, 1500)
        Handler().postDelayed({ AdsManage.ActivityBuilder().Load_InterstitialAd(this@MainActivity,false) }, 1500)
        AdsManage.ActivityBuilder().Load_RewardedAd(this,false)


        binding.btnSub.setOnClickListener {
            if (BaseSharedPreferences(this).mIS_SUBSCRIBED!!){
                Toast.makeText(this, "You PRO User", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(
                    Intent(this, SubscriptionBackgroundActivity::class.java)
                    .putExtra("AppOpen", "SettingsActivity"))
            }
        }
        binding.btnInterstitialOne.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialInterfaceAds(this,false,object : OnInterAdsShowAds {
                override fun OnDismissAds() {

                }

                override fun OnError() {

                }
            })
        }
        binding.btnInterstitialTwo.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialAds(this,false, null)
        }
        binding.btnInterstitialThree.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialAds(this,false, null)
        }
        binding.btnRewardeFour.setOnClickListener {
            AdsManage.ActivityBuilder().Show_RewardedAd(this,false, object : OnRewardedShowAds {
                override fun OnDismissAds() {

                }

                override fun OnUserEarned() {

                }

                override fun OnError() {

                }
            })
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}