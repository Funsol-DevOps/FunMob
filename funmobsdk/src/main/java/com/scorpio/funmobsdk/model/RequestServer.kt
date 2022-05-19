package com.scorpio.funmobsdk.model

/**
 * Class to make request to server
 * @param app_id: packageName of the app from where request is sending.
 * @param country: country of the user.
 */
data class RequestServer(
    val app_id: String,
    val country: String
)