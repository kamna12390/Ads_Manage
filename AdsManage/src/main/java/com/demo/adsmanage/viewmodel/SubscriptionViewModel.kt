package com.demo.adsmanage.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.adsmanage.Activity.PrivacyActivity
import com.demo.adsmanage.Activity.SubscriptionActivity.Companion.plans
import com.demo.adsmanage.Commen.Constants.PREMIUM_SIX_SKU
import com.demo.adsmanage.Commen.Constants.PREMIUM_SKU
import com.demo.adsmanage.Commen.Constants.SUBButtonTextColor
import com.demo.adsmanage.Commen.Constants.SubscriptionBackground
import com.demo.adsmanage.Commen.Constants.mAppIcon
import com.demo.adsmanage.Commen.Constants.mAppName
import com.demo.adsmanage.Commen.Constants.mAppNameColor
import com.demo.adsmanage.Commen.Constants.mClose_Icon
import com.demo.adsmanage.Commen.Constants.mIsRevenuCat
import com.demo.adsmanage.Commen.Constants.mPremiumScreenLine
import com.demo.adsmanage.Commen.Constants.mPremium_Button_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_CardSelected_Icon
import com.demo.adsmanage.Commen.Constants.mPremium_Cardunselected_Icon
import com.demo.adsmanage.Commen.Constants.mPriceBackground
import com.demo.adsmanage.Commen.Constants.mPriceLineColor
import com.demo.adsmanage.Commen.Constants.mSmallSubLineColor
import com.demo.adsmanage.Commen.Constants.mSubLineColor
import com.demo.adsmanage.Commen.Constants.packagerenlist
import com.demo.adsmanage.SubscriptionBaseClass.manager.PreferencesKeys
import com.demo.adsmanage.SubscriptionBaseClass.manager.SubscriptionManager
import com.demo.adsmanage.billing.ProductPurchaseHelper.getProductInfo
import com.demo.adsmanage.databinding.ActivitySubscriptionBinding
import com.demo.adsmanage.helper.IconPosition
import com.demo.adsmanage.helper.click
import com.demo.adsmanage.helper.getMonthBaseYearlyDiscount
import com.demo.adsmanage.helper.getSubTrial
import com.demo.adsmanage.helper.gone
import com.demo.adsmanage.helper.isOnline
import com.demo.adsmanage.helper.logD
import com.demo.adsmanage.helper.setCloseIconPosition
import com.demo.adsmanage.helper.showToast
import com.demo.adsmanage.Activity.TermsActivity
import com.demo.adsmanage.Commen.Constants
import com.demo.adsmanage.R
import org.jetbrains.anko.textColor


class SubscriptionViewModel (
    var binding: ActivitySubscriptionBinding,
    var mActivity: AppCompatActivity,
    var liveDataPeriod: MutableLiveData<HashMap<String, String>>,
    var liveDataPrice: MutableLiveData<HashMap<String, String>>,
    var subscriptionManager: SubscriptionManager,
    var isSelecterdPlan: IsSelecterdPlan,

    ) : ViewModel() {
    @SuppressLint("AnnotateVersionCheck")
    fun isPiePlus() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    val idname = arrayOf("_one", "_two", "_three", "_four", "_five", "_six", "_seven", "_eight")

    init {
        onMain()
    }

    interface IsSelecterdPlan {
        fun monMonthPlan()
        fun monYearPlan()
        fun monBackPress()
    }

    fun onMain() {
        setUI()
        setSubScriptionUI()
        setLineView()
        initListener()
    }

    @SuppressLint("SetTextI18n")
    fun setSubScriptionUI() {
        with(binding) {
            txtAppname.textColor= mActivity.resources.getColor(mAppNameColor!!)
            txtMessage.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtMonthBottom.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtYearBottom.textColor= mActivity.resources.getColor(mSubLineColor!!)
            txtFeature.textColor= mActivity.resources.getColor(mSmallSubLineColor!!)
            txtBottom.textColor= mActivity.resources.getColor(mSmallSubLineColor!!)
            txtBtnCondition.textColor= mActivity.resources.getColor(mSmallSubLineColor!!)
            txtBtnPrivacy.textColor= mActivity.resources.getColor(mSmallSubLineColor!!)
            txtAutoRenewMonth.textColor= mActivity.resources.getColor(mPriceLineColor!!)
            txtAutoRenewYear.textColor= mActivity.resources.getColor(mPriceLineColor!!)
            txtMonthlyPrice.textColor= mActivity.resources.getColor(mPriceLineColor!!)
            txtYearlyPrice.textColor= mActivity.resources.getColor(mPriceLineColor!!)
            txtUnlockKriadl.textColor=mActivity.resources.getColor(SUBButtonTextColor!!)
            imgBg.background=SubscriptionBackground
            mCVMonthLayout.background=mPriceBackground
            mCVYearLayout.background=mPriceBackground
            if (mIsRevenuCat!!) {
                packagerenlist!![0].sku.let {
                    if (it == "") {
                        txtMonthBottom.visibility = View.GONE
                    } else {
                        txtMonthlyPrice.text = packagerenlist!![0].price
                        txtMonthBottom.text = "Enjoy ${
                            packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                getSubTrial(it1)
                            }
                        } Free Trial"
                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                            val size = it1.length
                            val period = it1.substring(1, size - 1)
                            val str = it1.substring(size - 1, size)
                            Log.d("TAG", "getSubTrial: ${size} $period - $str")
                            when (str) {
                                "D" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                "W" -> {
                                    try {
                                        if (period.toInt() == 1) txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                        else txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                    } catch (e: Exception) {
                                        txtFeature.text =
                                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                                    getSubTrial(
                                                        it1
                                                    )
                                                }
                                            } trial"
                                    }
                                }
                                "M" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                "Y" -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else -> txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                    }
                }
                packagerenlist!![1].sku.let {

                    if (it == "") {
                        Log.d("enjoy", "onCreate:enjoy <-------------> 2")
                        txtYearlyPrice.visibility = View.GONE
                    } else {
                        txtYearlyPrice.text = packagerenlist!![1].price
                    }


                    packagerenlist?.get(1)?.price?.let { it1 ->
                        getMonthBaseYearlyDiscount(
                            packagerenlist?.get(0)?.price!!, it1
                        ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
                            txtYearBottom.text =
                                "${
                                    yearlyMonthBaseDiscountPrice.replace(
                                        ".00",
                                        ""
                                    )
                                }/month as BEST price"
                        }
                    }
                }
            } else {
                liveDataPeriod.observe(mActivity) { trial ->
                    liveDataPrice.observe(mActivity) { price ->
                        price[PREMIUM_SIX_SKU]?.let { month ->
                            price[PREMIUM_SKU]?.let { year ->
                                getMonthBaseYearlyDiscount(
                                    month,
                                    year
                                ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
                                    if (yearlyMonthBaseDiscountPrice.equals("₹590.00")) {
                                        txtYearBottom.text =
                                            "₹275/month as BEST price"
                                    } else {
                                        txtYearBottom.text =
                                            "${
                                                yearlyMonthBaseDiscountPrice.replace(
                                                    ".00",
                                                    ""
                                                )
                                            }/month as BEST price"
                                    }
                                }
                            }
                        }
                        PREMIUM_SKU.getProductInfo?.let { year ->
                            PREMIUM_SIX_SKU.getProductInfo?.let { month ->
                                if (year.freeTrialPeriod.equals("Not Found", true)) {
                                    txtUnlockKriadl.text = "Continue"
                                } else {
                                    txtUnlockKriadl.text = "start free trial"
                                }
                                if (month.freeTrialPeriod.equals("Not Found", true)) {
                                    txtMonthBottom.gone
                                } else {
                                    txtMonthBottom.text = "Enjoy ${
                                        getSubTrial(
                                            subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"")
                                        )
                                    } FREE Trial"
                                }
                            }
                        }
//                        price[Constants.PREMIUM_SIX_SKU]?.let {
//                            it
//                        }?.
//                        let {
//                            price[PREMIUM_SKU]?.let {
//                                it
//                            }?.let { it1 ->
//                                com.example.demo.subscriptionbackgroundflow.helper.getMonthBaseYearlyDiscount(
//                                    it, it1
//                                ) { yearlyDiscountPercentage, yearlyMonthBaseDiscountPrice ->
//                                    Log.d("TAG", "ondffgCreate: $yearlyMonthBaseDiscountPrice")
//                                    if (yearlyMonthBaseDiscountPrice.equals("₹590.00")) {
//                                        txtYearBottom.text =
//                                            "₹275/month as BEST price"
//                                    } else {
//                                        txtYearBottom.text =
//                                            "${
//                                                yearlyMonthBaseDiscountPrice.replace(
//                                                    ".00",
//                                                    ""
//                                                )
//                                            }/month as BEST price"
//                                    }
//                                }
//                            }
//                        }
                    txtYearlyPrice.text = "${
                        price[PREMIUM_SKU]?.let {
                            it
                        }
                    }"
                    txtMonthlyPrice.text = "${
                        price[PREMIUM_SIX_SKU]?.let {
                            it
                        }
                    }"
                }

//                    trial[PREMIUM_SKU]?.let {
//                        if (it == "") {
//                            txtUnlockKriadl.text = "Continue"
//                        } else {
//                            txtUnlockKriadl.text = "start free trial"
//                        }
//                    }

//                    subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"").let {
//                        if (it == "") {
//                            txtMonthBottom.gone
//                        } else {
//                            txtMonthBottom.text = "Enjoy ${
//                                getSubTrial(
//                                    it
//                                )
//                            } Free Trial"
//                        }
//                    }
            }
        }
    }
}

@SuppressLint("NewApi", "SetTextI18n")
fun setUI() {
    with(binding) {
        if (isPiePlus()) {
            mActivity.setCloseIconPosition(
                fParentLayout = clMainLayout, // Parent Constraint Layout
                fCloseIcon = imgClose, // Image View
                fIconPosition = IconPosition.RIGHT // IconPosition Left or Right
            )
        }
        imgClose.setImageDrawable(mClose_Icon)
        imgAppIcon.setImageDrawable(mAppIcon)
        mCLUnlockLayout.setBackgroundDrawable(mPremium_Button_Icon)
        txtAppname.text = mAppName
        mIVMonthSelection.background = mPremium_Cardunselected_Icon
        mIVYearSelection.background = mPremium_CardSelected_Icon

    }
}

@SuppressLint("DiscouragedApi")
private fun setLineView() {
    with(binding) {
        for (i in 0..7) {
            logD(
                "mPremiumScreenLine",
                "----$i--${mPremiumScreenLine!!.size}--${idname[i]}"
            )
            if (mPremiumScreenLine!!.size <= i) {
                val id_name = "txt${idname[i]}"
                val redId =
                    mActivity.resources.getIdentifier(id_name, "id", mActivity.packageName)
                val txt: TextView = mActivity.findViewById(redId)
                txt.visibility = View.GONE

                val id_name1 = "img_true${idname[i]}"
                val redId1 =
                    mActivity.resources.getIdentifier(id_name1, "id", mActivity.packageName)
                val img_true: ImageView = mActivity.findViewById(redId1)
                img_true.visibility = View.GONE

            } else {
                val id_name = "txt${idname[i]}"
                val redId =
                    mActivity.resources.getIdentifier(id_name, "id", mActivity.packageName)
                val txt: TextView = mActivity.findViewById(redId)
                txt.visibility = View.VISIBLE
                txt.text = mPremiumScreenLine!![i].mLine
                txt.textColor=mActivity.resources.getColor(mPremiumScreenLine!![i].mLineColor)

                val id_name1 = "img_true${idname[i]}"
                val redId1 =
                    mActivity.resources.getIdentifier(id_name1, "id", mActivity.packageName)
                val img_true: ImageView = mActivity.findViewById(redId1)
                img_true.visibility = View.VISIBLE
                img_true.setImageDrawable(mPremiumScreenLine!![i].mIconLine)
            }

        }
    }
}

private fun initListener() {
    with(binding) {
        binding.imgClose.click {
            isSelecterdPlan.monBackPress()
        }
        mCLMonthLayout.setOnClickListener {
            mIVYearSelection.background = mPremium_Cardunselected_Icon
            mIVMonthSelection.background = mPremium_CardSelected_Icon
            if (mIsRevenuCat!!) {
                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} at FREE trial, Then Enjoy ${
                                packagerenlist?.get(0)?.price
                            }/Days"
                        "W" -> {
                            try {
                                if (period.toInt() == 1) txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            } catch (e: Exception) {
                                txtFeature.text =
                                    "${packagerenlist?.get(0)?.price}/month after FREE ${
                                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                        "M" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "Y" -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        else -> txtFeature.text =
                            "${packagerenlist?.get(0)?.price}/month after FREE ${
                                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                    }
                    txtFeature.text = "${packagerenlist?.get(0)?.price}/month after FREE ${
                        packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                            getSubTrial(it1)
                        }
                    } trial"
                }
            } else {
                PREMIUM_SIX_SKU.getProductInfo?.let { month ->
                    if (month.freeTrialPeriod.equals("Not Found", true)) {
                        txtUnlockKriadl.text = "Continue"
                    } else {
                        txtUnlockKriadl.text = "start free trial"
                    }
                }
            }
//                    liveDataPeriod.observe(mActivity) { trial ->
//                        subscriptionManager.getString(PreferencesKeys.MONTH_TRIAL_PERIOD,"").let {
//                            if (it == "") {
//                                txtUnlockKriadl.text = "Continue"
//                            } else {
//                                txtUnlockKriadl.text = "start free trial"
//                            }
//                        }
//                    }
//                }
            plans = PREMIUM_SIX_SKU

        }
        mCLYearLayout.setOnClickListener {
            mIVYearSelection.background = mPremium_CardSelected_Icon
            mIVMonthSelection.background = mPremium_Cardunselected_Icon
            if (mIsRevenuCat!!) {
                packagerenlist?.get(0)?.freeTrialPeriod?.let { it1 ->
                    val size = it1.length
                    val period = it1.substring(1, size - 1)
                    val str = it1.substring(size - 1, size)
                    Log.d("TAG", "getSubTrial: ${size} $period - $str")
                    when (str) {
                        "D" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "W" -> {
                            try {
                                if (period.toInt() == 1) txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                                else txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            } catch (e: Exception) {
                                txtFeature.text =
                                    "${packagerenlist?.get(1)?.price}/year after FREE ${
                                        packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                            getSubTrial(
                                                it1
                                            )
                                        }
                                    } trial"
                            }
                        }
                        "M" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        "Y" -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                        else -> txtFeature.text =
                            "${packagerenlist?.get(1)?.price}/year after FREE ${
                                packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 ->
                                    getSubTrial(
                                        it1
                                    )
                                }
                            } trial"
                    }
//                    txtFeature.text = "${packagerenlist?.get(1)?.price}/yearly after FREE ${packagerenlist?.get(1)?.freeTrialPeriod?.let { it1 -> getSubTrial(it1) }} trial"
                }
            } else {
                PREMIUM_SKU.getProductInfo?.let { year ->
                    if (year.freeTrialPeriod.equals("Not Found", true)) {
                        txtUnlockKriadl.text = "Continue"
                    } else {
                        txtUnlockKriadl.text = "start free trial"
                    }
                }
//                    liveDataPeriod.observe(mActivity) { trial ->
//                        trial[PREMIUM_SKU]?.let {
//                            if (it == "") {
//                                txtUnlockKriadl.text = "Continue"
//                            } else {
//                                txtUnlockKriadl.text = "start free trial"
//                            }
//                        }
//                    }

            }
            plans = PREMIUM_SKU

        }

        txtBtnPrivacy.click {
//            mActivity.startActivity(Intent(mActivity, PrivacyActivity::class.java))

            val url= Constants.mPrivacyPolicyURL
            val customIntent = CustomTabsIntent.Builder()
            customIntent.setToolbarColor(ContextCompat.getColor(mActivity, R.color.white))
            openCustomTab(mActivity, customIntent.build(), Uri.parse(url))
        }
        txtBtnCondition.click {
            mActivity.startActivity(Intent(mActivity, TermsActivity::class.java))
        }
        mCLUnlockLayout.setOnClickListener {
            if (mActivity.isOnline) {
                when (plans) {
                    PREMIUM_SIX_SKU -> {
                        isSelecterdPlan.monMonthPlan()
                    }
                    PREMIUM_SKU -> {
                        isSelecterdPlan.monYearPlan()
                    }

                }
            } else {
                mActivity.showToast(
                    "Please check internet connection.",
                    android.widget.Toast.LENGTH_SHORT
                )
            }
        }
    }



}
    fun openCustomTab(activity: Activity, customTabsIntent: CustomTabsIntent, uri: Uri?) {
        // package name is the default package
        // for our custom chrome tab
        val packageName = "com.android.chrome"

        // we are checking if the package name is not null
        // if package name is not null then we are calling
        // that custom chrome tab with intent by passing its
        // package name.
        customTabsIntent.intent.setPackage(packageName)

        // in that custom tab intent we are passing
        // our url which we have to browse.
        customTabsIntent.launchUrl(activity, uri!!)
    }

}