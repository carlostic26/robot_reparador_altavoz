package com.blogspot.robotaltavoz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.blogspot.robotaltavoz.ads.Ads
import com.blogspot.robotaltavoz.screens.LoadingScreenActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoadingScreenActivity::class.java)
        startActivity(intent)
        finish()
    }


}