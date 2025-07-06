package com.example.digipic

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var forgotText: TextView
    private lateinit var signupText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "Login"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        // Find views
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.getStartedButton)
        forgotText = findViewById(R.id.tvForgot)
        signupText = findViewById(R.id.tvSignup)
        progressBar = ProgressBar(this).apply {
            visibility = View.GONE
        }

        loginButton.setOnClickListener {
            val email = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, DashBoard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        forgotText.setOnClickListener {
            val email = etUsername.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = View.VISIBLE
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Password reset email sent.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                "Failed to send reset email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        signupText.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}
