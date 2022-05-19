package com.scorpio.funmobsdk.retrofit

import com.scorpio.funmobsdk.model.AppAdData
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AdRequestService {
    @GET
    suspend fun requestAppAdData(
        @Header("Authorization") authorization: String,
        @Url url: String
//        @Field("country") country: String,
//        @Field("package_name") package_name: String
    ): AppAdData?
}