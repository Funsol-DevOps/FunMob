package com.scorpio.funmobsdk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppAdData(
    val id: String?,
    val name: String?,
    val description: String?,
    val rating: Float?,
    val total_reviews: Int?,
    val installs: String?,
    val updated_on: String?,
    val current_version: String?,
    val requires_android: String?,
    val content_rating: Int?,
    val size: Double?,
    val publisher: String?,
    val playstore_link: String?,
    val category: String?,
    val package_name: String?,
    val image_URL: String?,
    val media_URL: String?,
    val isVideo: Boolean
): Parcelable
