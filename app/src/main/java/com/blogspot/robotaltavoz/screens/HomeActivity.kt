package com.blogspot.robotaltavoz.screens

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.blogspot.robotaltavoz.R
import com.google.android.gms.ads.*

class HomeActivity : AppCompatActivity() {
    private val mediaPlayers = ArrayList<MediaPlayer>()
    private val cardViews = ArrayList<CardView>()

    private lateinit var mAdView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val seg10 = MediaPlayer.create(this, R.raw.diez_s)
        val seg25 = MediaPlayer.create(this, R.raw.veinticinco_s)
        val seg50 = MediaPlayer.create(this, R.raw.cincuenta_s)
        val min1 = MediaPlayer.create(this, R.raw.uno_m)
        val min3 = MediaPlayer.create(this, R.raw.tres_m)
        val min5 = MediaPlayer.create(this, R.raw.cinco_m)

        mediaPlayers.addAll(listOf(seg10, seg25, seg50, min1, min3, min5))

        setCardViewClickListener(R.id.cv_10s, seg10)
        setCardViewClickListener(R.id.cv_25s, seg25)
        setCardViewClickListener(R.id.cv_50s, seg50)
        setCardViewClickListener(R.id.cv_1m, min1)
        setCardViewClickListener(R.id.cv_3m, min3)
        setCardViewClickListener(R.id.cv_5m, min5)

        cardViews.addAll(listOf(
            findViewById(R.id.cv_10s),
            findViewById(R.id.cv_25s),
            findViewById(R.id.cv_50s),
            findViewById(R.id.cv_1m),
            findViewById(R.id.cv_3m),
            findViewById(R.id.cv_5m)
        ))

        loadBanner()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@HomeActivity, TutorialActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadBanner() {
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

    }


    private fun setCardViewClickListener(cardViewId: Int, mediaPlayer: MediaPlayer) {
        val cardView = findViewById<CardView>(cardViewId)
        cardView.setOnClickListener {
            Toast.makeText(this, "Agita tu teléfono", Toast.LENGTH_LONG).show()
            stopAllMediaPlayers()
            mediaPlayer.start()
            cardView.setCardBackgroundColor(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
            mediaPlayer.setOnCompletionListener {
                cardView.setCardBackgroundColor(ContextCompat.getColor(this, androidx.cardview.R.color.cardview_dark_background))
                Toast.makeText(this, "Tiempo de sonido finalizado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopAllMediaPlayers() {
        mediaPlayers.forEach { mediaPlayer ->
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                mediaPlayer.seekTo(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayers.forEach { it.release() }
    }
}
