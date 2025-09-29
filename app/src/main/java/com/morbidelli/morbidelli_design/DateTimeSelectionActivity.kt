package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class DateTimeSelectionActivity : AppCompatActivity() {

    private lateinit var btnClose: ImageButton
    private lateinit var tvMonthYear: TextView
    private lateinit var btnPrevMonth: ImageButton
    private lateinit var btnNextMonth: ImageButton
    private lateinit var tvTimeZone: TextView
    private lateinit var switch24h: Switch
    private lateinit var rgTimeSlots: RadioGroup
    private lateinit var tvContactUs: TextView
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button

    private var currentDate = Calendar.getInstance()
    private var selectedTimeSlot: String? = null
    private var is24HourFormat = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_time_selection)
        
        initViews()
        setupClickListeners()
        setupCalendar()
        setupTimeSlots()
    }
    
    private fun initViews() {
        btnClose = findViewById(R.id.btn_close)
        tvMonthYear = findViewById(R.id.tv_month_year)
        btnPrevMonth = findViewById(R.id.btn_prev_month)
        btnNextMonth = findViewById(R.id.btn_next_month)
        tvTimeZone = findViewById(R.id.tv_time_zone)
        switch24h = findViewById(R.id.switch_24h)
        rgTimeSlots = findViewById(R.id.rg_time_slots)
        tvContactUs = findViewById(R.id.tv_contact_us)
        btnBack = findViewById(R.id.btn_back)
        btnNext = findViewById(R.id.btn_next)
    }
    
    private fun setupClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        
        btnBack.setOnClickListener {
            finish()
        }
        
        btnNext.setOnClickListener {
            if (selectedTimeSlot != null) {
                proceedToNextStep()
            } else {
                Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show()
            }
        }
        
        btnPrevMonth.setOnClickListener {
            currentDate.add(Calendar.MONTH, -1)
            updateCalendar()
        }
        
        btnNextMonth.setOnClickListener {
            currentDate.add(Calendar.MONTH, 1)
            updateCalendar()
        }
        
        tvTimeZone.setOnClickListener {
            showTimeZoneDialog()
        }
        
        switch24h.setOnCheckedChangeListener { _, isChecked ->
            is24HourFormat = isChecked
            updateTimeSlots()
        }
        
        tvContactUs.setOnClickListener {
            // Handle contact us action
            Toast.makeText(this, "Contact us clicked", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupCalendar() {
        updateCalendar()
    }
    
    private fun updateCalendar() {
        val monthYearFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        tvMonthYear.text = monthYearFormat.format(currentDate.time)
        
        // Here you would populate the calendar grid with dates
        // For now, we'll just show the month/year
    }
    
    private fun setupTimeSlots() {
        val timeSlots = listOf(
            "7:00 am - 7:30 am",
            "7:30 am - 8:00 am",
            "8:00 am - 8:30 am",
            "8:30 am - 9:00 am",
            "9:00 am - 9:30 am",
            "9:30 am - 10:00 am",
            "10:00 am - 10:30 am",
            "10:30 am - 11:00 am",
            "11:00 am - 11:30 am",
            "11:30 am - 12:00 pm",
            "12:00 pm - 12:30 pm"
        )
        
        rgTimeSlots.removeAllViews()
        
        timeSlots.forEachIndexed { index, timeSlot ->
            val radioButton = RadioButton(this)
            radioButton.text = timeSlot
            radioButton.id = View.generateViewId()
            radioButton.textSize = 14f
            radioButton.setTextColor(getColor(R.color.black))
            radioButton.setPadding(16, 16, 16, 16)
            radioButton.background = getDrawable(R.drawable.time_slot_background)
            
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedTimeSlot = timeSlot
                }
            }
            
            rgTimeSlots.addView(radioButton)
        }
    }
    
    private fun updateTimeSlots() {
        // Update time slots based on 12/24 hour format
        setupTimeSlots()
    }
    
    private fun showTimeZoneDialog() {
        // Show time zone selection dialog
        Toast.makeText(this, "Time zone selection", Toast.LENGTH_SHORT).show()
    }
    
    private fun proceedToNextStep() {
        // Navigate to the final confirmation screen
        val intent = android.content.Intent(this, BookingConfirmationActivity::class.java)
        startActivity(intent)
        finish()
    }
}
