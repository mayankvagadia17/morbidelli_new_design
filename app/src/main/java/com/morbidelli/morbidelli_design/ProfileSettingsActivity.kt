package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileSettingsActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvUploadPhoto: TextView
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnChangePassword: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        tvUploadPhoto = findViewById(R.id.tv_upload_photo)
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etEmail = findViewById(R.id.et_email)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        btnChangePassword = findViewById(R.id.btn_change_password)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        tvUploadPhoto.setOnClickListener {
            showToast("Upload Photo clicked")
            // TODO: Implement photo upload functionality
        }
        
        btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
