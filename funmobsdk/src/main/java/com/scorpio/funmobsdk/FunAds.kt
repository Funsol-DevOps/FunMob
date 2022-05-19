package com.scorpio.funmobsdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import com.scorpio.funmobsdk.interfaces.FunAdsCallbacks
import com.scorpio.funmobsdk.interfaces.FunInterstitialCallbacks
import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.utils.Constants.TAG
import com.scorpio.funmobsdk.viewModels.AdsViewModel
import com.scorpio.funmobsdk.views.InterstitialActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*


class FunAds(private val activity: Activity, private val auth_token: String) {

    var funInterstitialCallbacks: FunInterstitialCallbacks? = null
        set(value) {
            field = value
            staticFunInterstitialCallbacks = value
        }
    var funAdsCallbacks: FunAdsCallbacks? = null
    private val adsViewModel by lazy { AdsViewModel(activity.application) }

    fun showInterstitial() {
        if (adsViewModel.appAdData != null) {
            Log.i(TAG, "showInterstitial: FunAds is not null")
            if (!activity.isFinishing) {
                val intent = Intent(activity, InterstitialActivity::class.java)
                intent.putExtra("appAdData", adsViewModel.appAdData)
                activity.startActivity(intent)
            }
        } else {
            Log.i(TAG, "showInterstitial: FunAds is null, requesting ads.")
            adsViewModel.requestAds(auth_token, activity.applicationContext.packageName, getCountryCode() ?: "") { isLoaded, appAdData, message ->
                if (isLoaded) {
                    showInterstitial()
                    appAdData?.let {
                        funAdsCallbacks?.onAdLoad(appAdData)
                    }
                }
            }
        }
    }

    fun loadAds() {
        adsViewModel.requestAds(auth_token, activity.applicationContext.packageName, getCountryCode() ?: "") { isLoaded, appAdData, message ->
            if (isLoaded) {
                appAdData?.let {
                    funAdsCallbacks?.onAdLoad(appAdData)
                }
            } else {
                appAdData?.let {
                    funAdsCallbacks?.onAdError(message ?: "")
                }
            }
        }
    }

    fun requestNativeAd(adRequestCallback: (AppAdData?) -> Unit) {
        if (adsViewModel.appAdData != null) {
            Log.i(TAG, "showNative: FunAds is not null")
            CoroutineScope(Main).launch {
                adRequestCallback(adsViewModel.appAdData!!)
            }
        } else {
            Log.i(TAG, "showNative: FunAds is null, requesting ads.")
            adsViewModel.requestAds(auth_token, activity.applicationContext.packageName, getCountryCode() ?: "") { isLoaded, appAdData, message ->
                if (isLoaded) {
                    CoroutineScope(Main).launch {
                        adsViewModel.appAdData?.let { adRequestCallback(it) }
                    }
                }
            }
        }
    }

    private fun getCountryCode(): String? {
        val telephonyManager =
            activity.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val countryCode = telephonyManager.networkCountryIso
        if (!TextUtils.isEmpty(countryCode)) {
            return countryCode
        }
        return Locale.getDefault().getCountry()
    }

    companion object {
        var staticFunInterstitialCallbacks: FunInterstitialCallbacks? = null
    }
}