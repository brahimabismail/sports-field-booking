package com.example.footbook

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val goNext = Runnable {
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<View?>(R.id.ivLogo)
        logo.setAlpha(0f)
        logo.setScaleX(0.8f)
        logo.setScaleY(0.8f)
        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(900)
            .start()

        handler.postDelayed(goNext, SPLASH_DELAY)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(goNext) // يمنع الانتقال إن أُغلقت الشاشة قبل الوقت
    }

    companion object {
        private const val SPLASH_DELAY: Long = 2500
    }
}