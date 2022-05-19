package com.scorpio.funmobsdk.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.retrofit.AdRequestService
import com.scorpio.funmobsdk.retrofit.AdRequestServiceHelper
import com.scorpio.funmobsdk.retrofit.AdRequestServiceImpl
import com.scorpio.funmobsdk.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdsViewModel constructor(application: Application) : AndroidViewModel(application) {

    var appAdData: AppAdData? = null

    init {
        /*appAdData = AppAdData(
            "id",
            "QR Scanner & Barcode scanner",
            "QR & Barcode Scanner is the quickest QR code scanner app / barcode reader & an essential QR reader for each android device.",
            4.7f,
            "1,000,000+",
            6.0,
            "Hi-Shot Inc",
            "https://play.google.com/store/apps/details?id=com.freescanner.qrcodereader.barcodescanner.barcodereader.socialmobileapps",
            "Tools",
            "com.freescanner.qrcodereader.barcodescanner.barcodereader.socialmobileapps",
            "https://play-lh.googleusercontent.com/PHmPfVRPMImlFZf1ArGdzSN0O1v0_vKppE_XLrRpFvt8wJuFpvqFe_PZxeNgGFgoE6ol=s360-rw",
            "https://play-lh.googleusercontent.com/tPxAA33U16pDYVUbz21K1RyjfJvDVkWFnIj_Q6lMrwbiqeo23TM1k4XR59KAltiHQl4=w1440-h620-rw",
            false
        )*/
    }

    fun requestAds(auth_token: String, packageName: String, country: String, adLoadCallback: (Boolean, AppAdData?, String?) -> Unit) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()

        val adRequestServiceHelper: AdRequestServiceHelper = AdRequestServiceImpl(retrofit.create(AdRequestService::class.java))

        if (appAdData == null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    adRequestServiceHelper.requestAppAdData(auth_token, packageName, country).let {
                        appAdData = it
                        adLoadCallback(true, it, "loaded")
                    }
                } catch (e: java.lang.Exception) {

                } catch (e: Exception) {
                }
            }
        } else {
            adLoadCallback(true, appAdData, "loaded")
        }

        /*appAdData = AppAdData(
            "id",
            "QR Scanner & Barcode scanner",
            "QR & Barcode Scanner is the quickest QR code scanner app / barcode reader & an essential QR reader for each android device.",
            4.7f,
            "1,000,000+",
            6.0,
            "Hi-Shot Inc",
            "https://play.google.com/store/apps/details?id=com.freescanner.qrcodereader.barcodescanner.barcodereader.socialmobileapps",
            "Tools",
            "com.freescanner.qrcodereader.barcodescanner.barcodereader.socialmobileapps",
            "https://play-lh.googleusercontent.com/PHmPfVRPMImlFZf1ArGdzSN0O1v0_vKppE_XLrRpFvt8wJuFpvqFe_PZxeNgGFgoE6ol=s360-rw",
            "https://www.youtube.com/watch?v=BKuFX7gJqFk",
            true
        )*/
    }
}