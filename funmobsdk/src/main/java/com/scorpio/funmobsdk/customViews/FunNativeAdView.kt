package com.scorpio.funmobsdk.customViews

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.utils.Constants

class FunNativeAdView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    var iconView: View? = null
        set(value) {
            field = value
            Glide.with(context).load(nativeAdData?.image_URL).into(field as ImageView)
            field?.setOnClickListener {
                nativeAdData?.let {
                    goToPlayStore(it.playstore_link ?: "", it.package_name ?: "")
                }
            }
        }

    var headingView: View? = null
        set(value) {
            field = value
            (field as TextView).text = nativeAdData?.name
            field?.setOnClickListener {
                nativeAdData?.let {
                    goToPlayStore(it.playstore_link ?: "", it.package_name ?:"")
                }
            }
        }

    var bodyView: View? = null
        set(value) {
            field = value
            (field as TextView).text = nativeAdData?.description
            field?.setOnClickListener {
                nativeAdData?.let {
                    goToPlayStore(it.playstore_link ?: "", it.package_name ?: "")
                }
            }
        }

    var callToActionView: View? = null
        set(value) {
            field = value
            field?.setOnClickListener {
                nativeAdData?.let {
                    goToPlayStore(it.playstore_link ?: "", it.package_name ?: "")
                }
            }
        }

    var mediaView: FunMediaView? = null
        set(value) {
            field = value
            nativeAdData?.let {
                Log.i(Constants.TAG, "showFunNativeAd: ${mediaView?.imageView}")
                if (it.isVideo) {

                    val videoId = it.media_URL?.substringAfter("watch?v=")
                    val iFrameVideo =
                        "<iframe width=\"950\" height=\"475\" src=\"https://www.youtube.com/embed/$videoId?autoplay=1&loop=1&controls=0&mute=1&playlist=$videoId&rel=\"0\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"

                    mediaView?.webView?.loadData(iFrameVideo, "text/html", "utf-8")

                    mediaView?.imageView?.visibility = View.GONE
                } else {
                    mediaView?.webView?.visibility = View.GONE
                    mediaView?.imageView?.visibility = View.VISIBLE
                    Glide.with(context).load(it.media_URL).into(mediaView?.imageView!!)
                }
            }
        }
    var advertiserView: View? = null
    var priceView: View? = null
    var ratingView: View? = null

    var nativeAdData: AppAdData? = null

    private fun goToPlayStore(link: String, packageName: String) {
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link)
                )
            )
        }
    }
}