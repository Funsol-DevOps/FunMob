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
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdsViewModel constructor(application: Application) : AndroidViewModel(application) {

    var appAdData: AppAdData? = null

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
                        Log.d("FunMobTag", "Ad Loaded $it")
                        withContext(Main) {
                            adLoadCallback(true, it, "loaded")
                        }
                    }
                } catch (e: java.lang.Exception) {
                    withContext(Main) {
                        adLoadCallback(false, null, "fail to load ads")
                    }
                    Log.d("FunMobTag", "Fail to load ad")
                } catch (e: Exception) {
                    withContext(Main) {
                        adLoadCallback(false, null, "fail to load ads")
                    }
                    Log.d("FunMobTag", "Fail to load ad")
                }
            }
        } else {
            Log.d("FunMobTag", "Ad Already Loaded $appAdData")
            CoroutineScope(Main).launch {
                adLoadCallback(true, appAdData, "loaded")
            }
        }
    }
}