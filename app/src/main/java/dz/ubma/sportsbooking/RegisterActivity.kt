package dz.ubma.sportsbooking

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            createAccount()
        }

        tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun createAccount() {

        val name = etFullName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()
        val confirmPassword =
            etConfirmPassword.text.toString()

        if (name.isEmpty()) {
            etFullName.error = "Enter your full name"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid email address"
            return
        }

        if (password.length < 6) {
            etPassword.error = "Password must contain at least 6 characters"
            return
        }

        if (password != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            return
        }

        Toast.makeText(
            this,
            "Information verified successfully!",
            Toast.LENGTH_SHORT
        ).show()
    }
}
