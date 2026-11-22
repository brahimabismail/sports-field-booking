package dz.ubma.sportsbooking

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    private val goToLogin = Runnable {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        handler.postDelayed(goToLogin, 2500)
    }

    override fun onDestroy() {
        handler.removeCallbacks(goToLogin)
        super.onDestroy()
    }
}
