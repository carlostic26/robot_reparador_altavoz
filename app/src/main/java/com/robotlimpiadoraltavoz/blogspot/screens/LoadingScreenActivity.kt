package com.robotlimpiadoraltavoz.blogspot.screens

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import androidx.core.content.ContextCompat
import com.robotlimpiadoraltavoz.blogspot.ads.Ads
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.robotlimpiadoraltavoz.blogspot.R


class LoadingScreenActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var loadingText: TextView
    private lateinit var continueButton: Button
    private lateinit var mediaPlayer: MediaPlayer

    private var appOpenAd: AppOpenAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        loadOpenAd()

        progressBar = findViewById(R.id.progressBar)
        loadingText = findViewById(R.id.loadingText)
        continueButton = findViewById(R.id.button)
        mediaPlayer = MediaPlayer.create(this, R.raw.splash)

        // Deshabilitar el botón de continuar al inicio
        continueButton.isEnabled = false

        appOpenAd?.show(this)

        val totalDuration = 10000L // 10 segundos

        val tickInterval = 100L // Actualizar cada 100 milisegundos

        // Configurar el máximo de la barra de progreso
        progressBar.max = (totalDuration / tickInterval).toInt()

        // Inicializar y comenzar la cuenta regresiva
        object : CountDownTimer(totalDuration, tickInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = ((totalDuration - millisUntilFinished) / totalDuration.toFloat() * progressBar.max).toInt()
                progressBar.progress = progress
            }

            override fun onFinish() {
                // Reproducir sonido
                mediaPlayer.start()
                loadingText.text = ""
                // Habilitar el botón de continuar cuando el tiempo termina
                continueButton.isEnabled = true
                continueButton.backgroundTintList = ContextCompat.getColorStateList(this@LoadingScreenActivity, R.color.colorAquamarine)
            }
        }.start()

        continueButton.setOnClickListener {
            val intent = Intent(this@LoadingScreenActivity, TutorialActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadOpenAd() {
        val ads = Ads()
        val adRequest = AdRequest.Builder().build()
        AppOpenAd.load(
            this, ads.openApp, adRequest,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    appOpenAd = ad
                    appOpenAd?.show(this@LoadingScreenActivity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    appOpenAd = null
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
