package com.demo.myadsmanage.Activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.myadsmanage.AdsManage
import com.demo.myadsmanage.InterFace.NativeAD
import com.demo.myadsmanage.InterFace.OnNativeAds
import com.demo.myadsmanage.InterFace.OnRewardedShowAds
import com.demo.myadsmanage.R
import com.demo.myadsmanage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.clOne.visibility = View.GONE
        binding.clTwo.visibility = View.GONE
        binding.clThree.visibility = View.GONE
        with(AdsManage.ActivityBuilder()) {
            Load_HOME_NativeAds(
                this@MainActivity,
                false,
                0,
                binding.adsNativeOne,
                R.layout.ads_native_layout,
                NativeAD.NativeFull,
                object : OnNativeAds {
                    override fun OnNativeAdsShow() {
                        binding.clOne.visibility = View.VISIBLE
                    }

                    override fun OnNativeAdsClick() {

                    }

                    override fun OnNativeAdsError() {
                        binding.clOne.visibility = View.GONE
                    }
                })
            Load_SETTING_NativeAds(
                this@MainActivity,
                false,
                1,
                binding.adsNativeTwo,
                R.layout.ads_native_layout,
                NativeAD.NativeFull,
                object : OnNativeAds {
                    override fun OnNativeAdsShow() {
                        binding.clTwo.visibility = View.VISIBLE
                    }

                    override fun OnNativeAdsClick() {

                    }

                    override fun OnNativeAdsError() {
                        binding.clTwo.visibility = View.GONE
                    }
                })
            Load_CREATION_NativeAds(
                this@MainActivity,
                false,
                1,
                binding.adsNativeThree,
                R.layout.ads_native_banner_layout,
                NativeAD.NariveBanner,
                object : OnNativeAds {
                    override fun OnNativeAdsShow() {
                        binding.clThree.visibility = View.VISIBLE
                    }

                    override fun OnNativeAdsClick() {

                    }

                    override fun OnNativeAdsError() {
                        binding.clThree.visibility = View.GONE
                    }
                })
            Show_AdaptiveBanner(this@MainActivity, false, binding.adsAdaptivebanner)
            Load_InterstitialAd(this@MainActivity, false, 0)
        }
        Handler().postDelayed(object : Runnable {
            override fun run() {
                AdsManage.ActivityBuilder().Load_InterstitialAd(this@MainActivity,false, 1)
            }
        }, 1500)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                AdsManage.ActivityBuilder().Load_InterstitialAd(this@MainActivity,false, 2)
            }
        }, 1500)
        AdsManage.ActivityBuilder().Load_RewardedAd(this,false)


        binding.btnInterstitialOne.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialAds(this,false, null, 0)
        }
        binding.btnInterstitialTwo.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialAds(this,false, null, 1)
        }
        binding.btnInterstitialThree.setOnClickListener {
            AdsManage.ActivityBuilder().Show_InterstitialAds(this,false, null, 2)
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
}