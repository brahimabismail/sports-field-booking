//package dz.ubma.sportsbooking
//
//import android.util.Log
//import android.os.SystemClock
//import android.os.Bundle
//import android.util.Patterns
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//class RegisterActivity : AppCompatActivity() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var db: FirebaseFirestore
//
//    private lateinit var etFullName: EditText
//    private lateinit var etEmail: EditText
//    private lateinit var etPassword: EditText
//    private lateinit var etConfirmPassword: EditText
//    private lateinit var btnRegister: Button
//    private val TAG = "REGISTER_FLOW"
//    private var startTime = 0L
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        auth = FirebaseAuth.getInstance()
//        db = FirebaseFirestore.getInstance()
//
//        setContentView(R.layout.activity_register)
//
//        etFullName = findViewById(R.id.etFullName)
//        etEmail = findViewById(R.id.etEmail)
//        etPassword = findViewById(R.id.etPassword)
//        etConfirmPassword = findViewById(R.id.etConfirmPassword)
//
//        btnRegister = findViewById(R.id.btnRegister)
//        val tvLogin = findViewById<TextView>(R.id.tvLogin)
//
//        btnRegister.setOnClickListener {
//            createAccount()
//        }
//
//        tvLogin.setOnClickListener {
//            finish()
//        }
//    }
//
//    private fun createAccount() {
//
//        val name = etFullName.text.toString().trim()
//        val email = etEmail.text.toString().trim()
//        val password = etPassword.text.toString()
//        val confirmPassword = etConfirmPassword.text.toString()
//
//        // Validate inputs
//
//        if (name.isEmpty()) {
//            etFullName.error = "Enter your full name"
//            return
//        }
//
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            etEmail.error = "Invalid email address"
//            return
//        }
//
//        if (password.length < 6) {
//            etPassword.error = "Password must contain at least 6 characters"
//            return
//        }
//
//        if (password != confirmPassword) {
//            etConfirmPassword.error = "Passwords do not match"
//            return
//        }
//        startTime = SystemClock.elapsedRealtime()
//        Log.d(TAG, "Registration started")
//
//        // Show loading state
//
//        btnRegister.isEnabled = false
//        btnRegister.text = "Creating account..."
//
//        Log.d(TAG, "Sending Authentication request")
//
//        // Create Firebase account
//
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) { task ->
//                Log.d(
//                    TAG,
//                    "Authentication callback after ${SystemClock.elapsedRealtime() - startTime} ms"
//                )
//
//                if (task.isSuccessful) {
//
//                    val firebaseUser = auth.currentUser
//
//                    if (firebaseUser == null) {
//                        showError("Account created, but session is unavailable.")
//                        return@addOnCompleteListener
//                    }
//
//                    val userId = firebaseUser.uid
//
//                    val user = hashMapOf(
//                        "uid" to userId,
//                        "fullName" to name,
//                        "email" to email,
//                        "role" to "user",
//                        "createdAt" to System.currentTimeMillis()
//                    )
//                    Log.d(TAG, "Sending Firestore request")
//
//                    // Save user profile in Firestore
//
//                    db.collection("users")
//                        .document(userId)
//                        .set(user)
//                        .addOnSuccessListener {
//                            Log.d(
//                                TAG,
//                                "Firestore SUCCESS after ${SystemClock.elapsedRealtime() - startTime} ms"
//                            )
//
//                            Toast.makeText(
//                                this,
//                                "Account created successfully!",
//                                Toast.LENGTH_LONG
//                            ).show()
//
//                            startActivity(
//                                Intent(this, HomeActivity::class.java)
//                            )
//
//                            finish()
//                        }
//                        .addOnFailureListener { exception ->
//                            Log.e(TAG, "Firestore FAILED", exception)
//
//                            showError(
//                                "Profile saving failed: ${exception.message}"
//                            )
//                        }
//
//                } else {
//
//                    val errorMessage =
//                        task.exception?.localizedMessage
//                            ?: "Something went wrong. Please try again."
//
//                    showError("Registration failed: $errorMessage")
//                }
//            }
//    }
//
//    private fun showError(message: String) {
//
//        btnRegister.isEnabled = true
//        btnRegister.text = "CREATE ACCOUNT"
//
//        Toast.makeText(
//            this,
//            message,
//            Toast.LENGTH_LONG
//        ).show()
//    }
//}


package dz.ubma.sportsbooking

import android.util.Log
import android.os.SystemClock
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private val TAG = "REGISTER_FLOW"
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setContentView(R.layout.activity_register)

        etFullName = findViewById(R.id.etFullName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnRegister = findViewById(R.id.btnRegister)
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
        val confirmPassword = etConfirmPassword.text.toString()

        // Validate inputs

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
        startTime = SystemClock.elapsedRealtime()
        Log.d(TAG, "Registration started")

        // Show loading state

        btnRegister.isEnabled = false
        btnRegister.text = "Creating account..."

        Log.d(TAG, "Sending Authentication request")

        // Create Firebase account

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                Log.d(
                    TAG,
                    "Authentication callback after ${SystemClock.elapsedRealtime() - startTime} ms"
                )

                if (task.isSuccessful) {

                    val firebaseUser = auth.currentUser

                    if (firebaseUser == null) {
                        showError("Account created, but session is unavailable.")
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
                    Log.d(TAG, "Sending Firestore request")

                    // Save user profile in Firestore.
                    // IMPORTANT: Firestore queues this write locally and syncs it in the
                    // background. We do NOT wait for the server acknowledgement
                    // (that is what took 154 seconds on a slow network).
                    // The listeners below are NOT tied to the Activity (no "this"),
                    // so they still run after finish().

                    db.collection("users")
                        .document(userId)
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(
                                TAG,
                                "Firestore SUCCESS (server confirmed) after ${SystemClock.elapsedRealtime() - startTime} ms"
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Firestore FAILED", exception)
                        }

                    // Go to Home immediately, without waiting for Firestore

                    Log.d(
                        TAG,
                        "Navigating to Home after ${SystemClock.elapsedRealtime() - startTime} ms"
                    )

                    Toast.makeText(
                        this,
                        "Account created successfully!",
                        Toast.LENGTH_LONG
                    ).show()

                    startActivity(
                        Intent(this, HomeActivity::class.java)
                    )

                    finish()

                } else {

                    val errorMessage =
                        task.exception?.localizedMessage
                            ?: "Something went wrong. Please try again."

                    showError("Registration failed: $errorMessage")
                }
            }
    }

    private fun showError(message: String) {

        btnRegister.isEnabled = true
        btnRegister.text = "CREATE ACCOUNT"

        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}