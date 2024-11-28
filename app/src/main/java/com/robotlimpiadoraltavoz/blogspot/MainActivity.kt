package com.robotlimpiadoraltavoz.blogspot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.robotlimpiadoraltavoz.blogspot.screens.LoadingScreenActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoadingScreenActivity::class.java)
        startActivity(intent)
        finish()
    }


}