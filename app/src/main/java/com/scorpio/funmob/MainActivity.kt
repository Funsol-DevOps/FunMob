package com.scorpio.funmob

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.scorpio.funmob.databinding.ActivityMainBinding
import com.scorpio.funmob.databinding.NativeSmallBinding
import com.scorpio.funmobsdk.FunAds
import com.scorpio.funmobsdk.customViews.FunMediaView
import com.scorpio.funmobsdk.customViews.FunNativeAdView
import com.scorpio.funmobsdk.interfaces.FunAdsCallbacks
import com.scorpio.funmobsdk.interfaces.FunInterstitialCallbacks
import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.utils.Constants.TAG

class MainActivity : AppCompatActivity(), FunAdsCallbacks, FunInterstitialCallbacks {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val funAds = FunAds(this@MainActivity, "auth_token_here")
        funAds.funInterstitialCallbacks = this
        funAds.funAdsCallbacks = this

        with(binding) {
            showInterstitial.setOnClickListener {
                funAds.showInterstitial()
            }

            funAds.requestNativeAd {
                showFunNativeAd(it, layoutNativeAds.parentNativeContainer, layoutNativeAds.nativeContainer, 70)
            }

            funAds.requestNativeAd {
                showFunNativeAd(it, layoutNativeAdsFull.parentNativeContainer, layoutNativeAdsFull.nativeContainer, 270)
            }
        }
    }

    private fun showFunNativeAd(appAdData: AppAdData?, parentNativeContainer: ConstraintLayout, nativeContainer: FrameLayout, height: Int) {
        if (appAdData != null) {
            val loadingText = nativeContainer.findViewById<TextView>(R.id.loading_ad)
            if (loadingText != null) loadingText.visibility = View.GONE
            val inflater = LayoutInflater.from(this)

            val adView: FunNativeAdView = when (height) {
                in 60..110 -> {
                    inflater.inflate(R.layout.native_small, null) as FunNativeAdView
                }
                in 111..270 -> {
                    inflater.inflate(R.layout.native_large, null) as FunNativeAdView
                }
                else -> {
                    inflater.inflate(R.layout.native_large, null) as FunNativeAdView
                }
            }

            nativeContainer.removeAllViews()
            nativeContainer.addView(adView)

            adView.nativeAdData = appAdData

            adView.iconView = adView.findViewById(R.id.ad_app_icon)
            adView.headingView = adView.findViewById(R.id.ad_headline)
            adView.bodyView = adView.findViewById(R.id.ad_body)
            adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)

            if (adView.findViewById<FunMediaView>(R.id.media_view) != null) {
                val mediaView: FunMediaView = adView.findViewById(R.id.media_view)
                adView.mediaView = mediaView
            }

//            adView.advertiserView = adView.findViewById(R.id.ad_headline)
//            adView.priceView = adView.findViewById(R.id.ad_headline)
//            adView.ratingView = adView.findViewById(R.id.ad_headline)
        } else {
            parentNativeContainer.visibility = View.GONE
        }
    }

    override fun onAdLoad(appAdData: AppAdData) {

    }

    override fun onAdError(error: String) {

    }

    override fun onAdImpression() {

    }

    override fun onAdClose() {

    }

    override fun onAdFailToShow() {

    }
}