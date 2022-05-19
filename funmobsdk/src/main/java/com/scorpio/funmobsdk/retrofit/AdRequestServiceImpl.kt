package com.scorpio.funmobsdk.retrofit

import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.utils.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response

class AdRequestServiceImpl constructor(private val adRequestService: AdRequestService) : AdRequestServiceHelper {
    override suspend fun requestAppAdData(auth_token: String, package_name: String, country: String): AppAdData? =
        adRequestService.requestAppAdData(
            auth_token,
            "${Constants.BASE_URL}api/?package_name=$package_name&country=$country"
        )

}
//{ "package_name": "com.facebook.katana","country": "pk" }