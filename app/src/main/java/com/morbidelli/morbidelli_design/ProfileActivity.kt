package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var btnClose: ImageButton
    private lateinit var llProfileSettings: LinearLayout
    private lateinit var llNotifications: LinearLayout
    private lateinit var llMessages: LinearLayout
    private lateinit var llTermsOfService: LinearLayout
    private lateinit var llTheme: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnClose = findViewById(R.id.btn_close)
        llProfileSettings = findViewById(R.id.ll_profile_settings)
        llNotifications = findViewById(R.id.ll_notifications)
        llMessages = findViewById(R.id.ll_messages)
        llTermsOfService = findViewById(R.id.ll_terms_of_service)
        llTheme = findViewById(R.id.ll_theme)
    }
    
    private fun setupClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        
        llProfileSettings.setOnClickListener {
            startActivity(Intent(this, ProfileSettingsActivity::class.java))
        }
        
        llNotifications.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        
        llMessages.setOnClickListener {
            startActivity(Intent(this, MessagesActivity::class.java))
        }
        
        llTermsOfService.setOnClickListener {
            startActivity(Intent(this, TermsOfServiceActivity::class.java))
        }
        
        llTheme.setOnClickListener {
            showToast("Theme clicked")
        }
    }
    
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
