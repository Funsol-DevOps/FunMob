package com.scorpio.funmobsdk.interfaces

import com.scorpio.funmobsdk.model.AppAdData

interface FunAdsCallbacks {
    fun onAdLoad(appAdData: AppAdData)
    fun onAdError(error: String)
}