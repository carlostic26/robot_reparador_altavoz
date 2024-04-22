package com.blogspot.robotaltavoz.screens

import android.util.Log

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.blogspot.robotaltavoz.R
import com.blogspot.robotaltavoz.ads.Ads
import com.bumptech.glide.Glide
import com.google.android.gms.ads.*

import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class TutorialActivity : AppCompatActivity() {

    private lateinit var continueButton: Button
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var bannerAd : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        loadInterstitialAd()
        initView()
        initImgGif()
        loadBanner()
    }
    private fun initView() {
        continueButton = findViewById(R.id.btnContinuarTutorial)

        continueButton.setOnClickListener {
            showConfirmationDialog()
        }

    }

    private fun initImgGif() {
        val gifImageView: ImageView = findViewById(R.id.gifImageView)
        val gifUrl = "https://blogger.googleusercontent.com/img/a/AVvXsEiCS8i3p-1NBuRSNUzpR29AyflACGWaHZA8GP92ERyOjTa3az7u9dH3tP5jgjyAL9BJSeeF1rH6XR2jGSNS697COHIkCPlEqQ077W1EKbjfOPsKNxIJocH9H5DGux3-FX3EGb7JesqkMbS0yMeox2PnUpdRD4WY1Ogx96OpHE5ijvHTcFmLjp1YtQdDFQ"

        Glide.with(this)
            .load(gifUrl)
            .placeholder(R.drawable.loading_placeholder)
            .into(gifImageView)
    }


    private fun loadInterstitialAd() {
        val ads = Ads()

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this, ads.interstitial, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d("AdLoadFailed", "Error: ${p0.message}")
                mInterstitialAd = null
            }


            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        continueToNextActivity()
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        // Si falla la visualización del anuncio, continúa con la acción prevista
                        continueToNextActivity()
                    }
                }
            }
        })
    }

    private fun loadBanner() {
        MobileAds.initialize(this) {}

        bannerAd = findViewById(R.id.bannerAdView)
        val adRequest = AdRequest.Builder().build()
        bannerAd.loadAd(adRequest)


    }

    private fun continueToNextActivity() {
        val intent = Intent(this@TutorialActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Antes de continuar")
            .setMessage("Una vez comience el sonido, agita el teléfono verticalmente. \n\nApóyanos viendo un anuncio de 5 seg mientras reparas tu altavoz. Es necesario para que la app pueda seguir existiendo.")
            .setPositiveButton("Continuar") { dialog, _ ->

                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@TutorialActivity)
                }else{
                    loadInterstitialAd()
                    Toast.makeText(this,"Inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                }


            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo
            }
            .setCancelable(false)

        val dialog = builder.create()
        dialog.show()
    }

}