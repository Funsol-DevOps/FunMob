package com.scorpio.funmobsdk.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.scorpio.funmobsdk.databinding.ActivityInterstitialBinding
import com.scorpio.funmobsdk.model.AppAdData
import com.scorpio.funmobsdk.utils.NumberConverter
import java.lang.StringBuilder

class InterstitialActivity : AppCompatActivity() {

    private val binding: ActivityInterstitialBinding by lazy { ActivityInterstitialBinding.inflate(layoutInflater) }
    private var appAdData: AppAdData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        @Suppress("Deprecation")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)

        appAdData = intent.getParcelableExtra("appAdData")

        init()
        initListeners()
    }

    private fun init() {
        if (appAdData == null)
            finish()

        with(binding) {
            appAdData?.let { data ->

                appName.text = data.name
                totalDownloads.text = NumberConverter().format(data.installs?.toLong() ?: 0)
                totalRating.text = StringBuilder().append(data.rating)

                Glide.with(this@InterstitialActivity)
                    .load(data.image_URL).into(appIcon)

                appIcon.setOnClickListener {
                    goToPlayStore(data.playstore_link ?: "", data.package_name ?: "")
                }

                btnInstall.setOnClickListener {
                    goToPlayStore(data.playstore_link ?: "", data.package_name ?: "")
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            btnClose.setOnClickListener { finish() }
        }
    }

    private fun goToPlayStore(link: String, packageName: String){
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(link)
                )
            )
        }
    }
}