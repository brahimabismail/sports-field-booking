package com.example.footbook

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {
    private var etEmail: TextInputEditText? = null
    private var etPassword: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById<TextInputEditText?>(R.id.etEmail)
        etPassword = findViewById<TextInputEditText?>(R.id.etPassword)
        val btnLogin = findViewById<Button?>(R.id.btnLogin)
        val tvRegister = findViewById<TextView?>(R.id.tvRegister)

        btnLogin.setOnClickListener(View.OnClickListener { v: View? -> attemptLogin() })

        tvRegister.setOnClickListener(View.OnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        })
    }

    private fun attemptLogin() {
        val email = etEmail!!.getText().toString().trim { it <= ' ' }
        val password = etPassword!!.getText().toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail!!.setError("بريد إلكتروني غير صالح")
            return
        }
        if (TextUtils.isEmpty(password) || password.length < 6) {
            etPassword!!.setError("6 أحرف على الأقل")
            return
        }

        // لاحقًا: التحقق من SQLite عبر DatabaseHelper
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}