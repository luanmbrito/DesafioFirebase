package com.example.desafiofirebase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.desafiofirebase.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val maxSplashTime: Long = 3000

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        },maxSplashTime)
    }
}