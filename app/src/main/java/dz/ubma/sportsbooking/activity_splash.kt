package dz.ubma.sportsbooking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val goNext = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val logo = findViewById<View>(R.id.ivLogo)

        logo.alpha = 0f
        logo.scaleX = 0.8f
        logo.scaleY = 0.8f

        logo.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(900)
            .start()

        handler.postDelayed(goNext, 2500)
    }

    override fun onDestroy() {
        handler.removeCallbacks(goNext)
        super.onDestroy()
    }
}
