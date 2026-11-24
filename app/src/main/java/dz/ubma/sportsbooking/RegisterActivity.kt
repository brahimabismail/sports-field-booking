package dz.ubma.sportsbooking

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
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

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val firebaseUser = auth.currentUser

                    if (firebaseUser == null) {
                        Toast.makeText(
                            this,
                            "Account created, but user session is unavailable.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@addOnCompleteListener
                    }

                    val userId = firebaseUser.uid

                    val user = hashMapOf(
                        "uid" to userId,
                        "fullName" to name,
                        "email" to email,
                        "role" to "user",
                        "createdAt" to System.currentTimeMillis()
                    )

                    db.collection("users")
                        .document(userId)
                        .set(user)
                        .addOnSuccessListener {

                            Toast.makeText(
                                this,
                                "Account created successfully!",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(this, HomeActivity::class.java)
                            )
                            finish()
                        }
                        .addOnFailureListener { exception ->

                            auth.currentUser?.delete()

                            Toast.makeText(
                                this,
                                "Database error: ${exception.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                } else {

                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
