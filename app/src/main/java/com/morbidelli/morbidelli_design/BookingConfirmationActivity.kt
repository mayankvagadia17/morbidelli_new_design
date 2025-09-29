package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingConfirmationActivity : AppCompatActivity() {

    private lateinit var btnClose: ImageButton
    private lateinit var ivMotorcycle: ImageView
    private lateinit var tvMotorcycleModel: TextView
    private lateinit var tvMotorcycleCategory: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvAddress: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserEmail: TextView
    private lateinit var btnBack: Button
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_confirmation)
        
        initViews()
        setupClickListeners()
        populateBookingDetails()
    }
    
    private fun initViews() {
        btnClose = findViewById(R.id.btn_close)
        ivMotorcycle = findViewById(R.id.iv_motorcycle)
        tvMotorcycleModel = findViewById(R.id.tv_motorcycle_model)
        tvMotorcycleCategory = findViewById(R.id.tv_motorcycle_category)
        tvLocation = findViewById(R.id.tv_location)
        tvAddress = findViewById(R.id.tv_address)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)
        tvUserName = findViewById(R.id.tv_user_name)
        tvUserEmail = findViewById(R.id.tv_user_email)
        btnBack = findViewById(R.id.btn_back)
        btnSubmit = findViewById(R.id.btn_submit)
    }
    
    private fun setupClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
        
        btnSubmit.setOnClickListener {
            submitBooking()
        }
    }
    
    private fun populateBookingDetails() {
        // Set motorcycle details
        tvMotorcycleModel.text = "T1002VX"
        tvMotorcycleCategory.text = "Trail"
        
        // Set location details
        tvLocation.text = "US, New Texas, Texas city"
        tvAddress.text = "Plot No. 1, KH No. 1008, 100 Ft Road, DPS Ro..."
        
        // Set date and time
        tvDate.text = "Wednesday, August 13, 2025"
        tvTime.text = "7:00 am - 7:30 am, GMT+8"
        
        // Set user details (these would come from the previous form)
        tvUserName.text = "Matias Johnson"
        tvUserEmail.text = "matiasjohnson@examplemail.com"
        
        // Set motorcycle image (you can replace with actual image resource)
        ivMotorcycle.setImageResource(R.drawable.ic_bike_n300) // Using existing bike icon as placeholder
    }
    
    private fun submitBooking() {
        // Show success message
        Toast.makeText(this, "Booking submitted successfully!", Toast.LENGTH_SHORT).show()
        
        // Navigate back to main screen or show success screen
        finish()
    }
}
