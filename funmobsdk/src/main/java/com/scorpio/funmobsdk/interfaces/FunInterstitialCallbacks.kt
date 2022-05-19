package com.scorpio.funmobsdk.interfaces

import kotlinx.android.parcel.Parcelize

interface FunInterstitialCallbacks {
    fun onAdImpression()
    fun onAdClose()
    fun onAdFailToShow()
}