package dz.ubma.sportsbooking

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegister)

        btnLogin.setOnClickListener {
            attemptLogin()
        }

        tvRegister.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }

    private fun attemptLogin() {

        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "بريد إلكتروني غير صالح"
            return
        }

        if (password.length < 6) {
            etPassword.error = "كلمة المرور يجب أن تحتوي على 6 أحرف على الأقل"
            return
        }

        Toast.makeText(
            this,
            "تم التحقق من البيانات بنجاح",
            Toast.LENGTH_SHORT
        ).show()
    }
}
