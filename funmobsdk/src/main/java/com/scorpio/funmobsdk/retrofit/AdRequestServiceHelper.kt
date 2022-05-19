package com.scorpio.funmobsdk.retrofit

import com.scorpio.funmobsdk.model.AppAdData
import retrofit2.Response

interface AdRequestServiceHelper {
    suspend fun requestAppAdData(package_name: String, country: String): AppAdData?
}