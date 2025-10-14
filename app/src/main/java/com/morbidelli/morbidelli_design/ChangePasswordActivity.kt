package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var etCurrentPassword: EditText
    private lateinit var etNewPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnChangePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        etCurrentPassword = findViewById(R.id.et_current_password)
        etNewPassword = findViewById(R.id.et_new_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnChangePassword = findViewById(R.id.btn_change_password)
    }
    
    private fun setupClickListeners() { 
        btnBack.setOnClickListener {
            finish()
        }
        
        btnChangePassword.setOnClickListener {
            validateAndChangePassword()
        }
    }
    
    private fun validateAndChangePassword() {
        val currentPassword = etCurrentPassword.text.toString().trim()
        val newPassword = etNewPassword.text.toString().trim()
        val confirmPassword = etConfirmPassword.text.toString().trim()
        
        // Validation
        if (currentPassword.isEmpty()) {
            etCurrentPassword.error = "Please enter current password"
            etCurrentPassword.requestFocus()
            return
        }
        
        if (newPassword.isEmpty()) {
            etNewPassword.error = "Please enter new password"
            etNewPassword.requestFocus()
            return
        }
        
        if (newPassword.length < 8) {
            etNewPassword.error = "Password must be at least 8 characters"
            etNewPassword.requestFocus()
            return
        }
        
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.error = "Please confirm new password"
            etConfirmPassword.requestFocus()
            return
        }
        
        if (newPassword != confirmPassword) {
            etConfirmPassword.error = "Passwords do not match"
            etConfirmPassword.requestFocus()
            return
        }
        
        // TODO: Implement actual password change logic
        // For now, just show success message
        Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
