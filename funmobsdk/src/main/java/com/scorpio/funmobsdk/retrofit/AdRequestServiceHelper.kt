package com.scorpio.funmobsdk.retrofit

import com.scorpio.funmobsdk.model.AppAdData
import retrofit2.Response

interface AdRequestServiceHelper {
    suspend fun requestAppAdData(auth_token: String, package_name: String, country: String): AppAdData?
}