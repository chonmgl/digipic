package com.example.digipic

import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {

    private lateinit var etPassword: EditText
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login) // Uses your login.xml layout

        // Initialize views
        val etUsername = findViewById<EditText>(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.getStartedButton)
        val tvSignup = findViewById<TextView>(R.id.tvSignup)

        // Toggle password visibility
        etPassword.setOnTouchListener { _, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                val drawable = etPassword.compoundDrawables[drawableEnd]
                if (drawable != null && event.rawX >= (etPassword.right - drawable.bounds.width())) {
                    togglePasswordVisibility()
                    etPassword.performClick()
                    return@setOnTouchListener true
                }
            }
            false
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString()

            if (username.isEmpty()) {
                etUsername.error = "Username is required"
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
            }

            if (username.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Login successful (mock)", Toast.LENGTH_SHORT).show()
                // Mock frontend-only behavior
            }
        }

        // Handle "Sign up" click with frontend message (no navigation)
        tvSignup.setOnClickListener {
            Toast.makeText(this, "Signup screen not implemented (frontend only)", Toast.LENGTH_SHORT).show()
        }
    }

    // Toggle show/hide password
    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0)
        } else {
            etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            etPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0)
        }
        etPassword.setSelection(etPassword.text.length)
    }
}
