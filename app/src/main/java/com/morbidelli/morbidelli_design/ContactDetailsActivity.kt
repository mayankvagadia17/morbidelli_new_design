package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var btnClose: ImageButton
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbTermsAndConditions: CheckBox
    private lateinit var tvTermsAndConditions: TextView
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        
        initViews()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnClose = findViewById(R.id.btn_close)
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etEmail = findViewById(R.id.et_email)
        cbTermsAndConditions = findViewById(R.id.cb_terms_and_conditions)
        tvTermsAndConditions = findViewById(R.id.tv_terms_and_conditions)
        btnBack = findViewById(R.id.btn_back)
        btnNext = findViewById(R.id.btn_next)
        
        // Set HTML formatted text for terms and conditions
        tvTermsAndConditions.text = Html.fromHtml("I agree with the <b>terms and conditions</b>", Html.FROM_HTML_MODE_LEGACY)
    }
    
    private fun setupClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
        
        btnNext.setOnClickListener {
//            if (validateForm()) {
                proceedToNextStep()
//            }
        }
        
        cbTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            updateNextButtonState()
        }
    }
    
    private fun validateForm(): Boolean {
        var isValid = true
        
        if (etFirstName.text.toString().trim().isEmpty()) {
            etFirstName.error = "First name is required"
            isValid = false
        }
        
        if (etLastName.text.toString().trim().isEmpty()) {
            etLastName.error = "Last name is required"
            isValid = false
        }
        
        if (etPhoneNumber.text.toString().trim().isEmpty()) {
            etPhoneNumber.error = "Phone number is required"
            isValid = false
        }
        
        if (etEmail.text.toString().trim().isEmpty()) {
            etEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString().trim()).matches()) {
            etEmail.error = "Please enter a valid email address"
            isValid = false
        }
        
        if (!cbTermsAndConditions.isChecked) {
            Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        
        return isValid
    }
    
    private fun updateNextButtonState() {
        btnNext.isEnabled = cbTermsAndConditions.isChecked
        btnNext.alpha = if (cbTermsAndConditions.isChecked) 1.0f else 0.6f
    }
    
    private fun proceedToNextStep() {
        // Navigate to the booking confirmation screen
        val intent = android.content.Intent(this, BookingConfirmationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
