package dz.ubma.sportsbooking

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var etEmail: EditText
    private lateinit var btnSendReset: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvMessage: TextView

    private var cooldownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.etEmail)
        btnSendReset = findViewById(R.id.btnSendReset)
        progressBar = findViewById(R.id.progressBar)
        tvMessage = findViewById(R.id.tvMessage)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)

        // Pre-fill the email typed on the Login screen (if any)
        intent.getStringExtra("email")?.let { etEmail.setText(it) }

        btnSendReset.setOnClickListener {
            sendResetLink()
        }

        // Keyboard "Send" button
        etEmail.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendResetLink()
                true
            } else {
                false
            }
        }

        tvBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun sendResetLink() {
        val email = etEmail.text.toString().trim()

        // Validate input
        if (email.isEmpty()) {
            etEmail.error = "Enter your email address"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid email address"
            return
        }

        tvMessage.visibility = View.GONE
        setLoading(true)

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                setLoading(false)

                if (task.isSuccessful) {
                    showSuccess()
                    return@addOnCompleteListener
                }

                when (val e = task.exception) {
                    // Account not found: show the same message as success,
                    // so nobody can find out which emails are registered.
                    is FirebaseAuthInvalidUserException -> showSuccess()

                    is FirebaseAuthInvalidCredentialsException ->
                        etEmail.error = "Invalid email address"

                    is FirebaseNetworkException ->
                        showMessage("No internet connection. Please check your network and try again.", false)

                    is FirebaseTooManyRequestsException ->
                        showMessage("Too many attempts. Please wait a few minutes and try again.", false)

                    else ->
                        showMessage(
                            e?.localizedMessage ?: "Something went wrong. Please try again.",
                            false
                        )
                }
            }
    }

    private fun showSuccess() {
        showMessage(
            "If an account exists for this email, a reset link has been sent. " +
                    "Check your inbox (and spam folder).",
            true
        )
        startCooldown()
    }

    private fun showMessage(message: String, success: Boolean) {
        tvMessage.text = message
        tvMessage.setTextColor(if (success) Color.parseColor("#7CFC9A") else Color.parseColor("#FF8A80"))
        tvMessage.visibility = View.VISIBLE
    }

    private fun setLoading(loading: Boolean) {
        progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        btnSendReset.isEnabled = !loading
        etEmail.isEnabled = !loading
        btnSendReset.text = if (loading) "Sending..." else "SEND RESET LINK"
    }

    // Prevents spamming: the user can request another link after 30 seconds
    private fun startCooldown() {
        btnSendReset.isEnabled = false
        cooldownTimer?.cancel()
        cooldownTimer = object : CountDownTimer(30_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                btnSendReset.text = "RESEND IN ${(millisUntilFinished + 999) / 1000}s"
            }

            override fun onFinish() {
                btnSendReset.isEnabled = true
                btnSendReset.text = "RESEND LINK"
            }
        }.start()
    }

    override fun onDestroy() {
        cooldownTimer?.cancel()
        super.onDestroy()
    }
}