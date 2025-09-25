package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.morbidelli.morbidelli_design.model.Booking
import com.morbidelli.morbidelli_design.model.BookingStatus

class BookingDetailActivity : AppCompatActivity() {

    private lateinit var booking: Booking

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_detail)

        // Get booking data from intent
        booking = intent.getSerializableExtra("booking") as? Booking ?: return

        setupToolbar()
        setupViews()
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViews() {
        // Set booking ID
        findViewById<TextView>(R.id.tv_booking_id).text = "#${booking.id}"

        // Set status badge
        val statusBadge = findViewById<TextView>(R.id.tv_status_badge)
        statusBadge.text = booking.status.name
        statusBadge.setBackgroundResource(
            when (booking.status) {
                BookingStatus.UPCOMING -> R.drawable.bg_status_upcoming
                BookingStatus.COMPLETED -> R.drawable.bg_status_completed
                BookingStatus.CANCELLED -> R.drawable.bg_status_cancelled
            }
        )

        // Set vehicle details
        findViewById<TextView>(R.id.tv_vehicle_model).text = "${booking.vehicleCode} - ${booking.vehicleModel}"
        findViewById<TextView>(R.id.tv_transmission).text = "Transmission: ${booking.transmission}"
        findViewById<TextView>(R.id.tv_color).text = "Color: ${booking.color}"

        // Set contact person details
        findViewById<TextView>(R.id.tv_contact_name).text = booking.contactPerson
        findViewById<TextView>(R.id.tv_contact_email).text = booking.contactEmail

        // Set location details
        findViewById<TextView>(R.id.tv_location_name).text = booking.companyName
        findViewById<TextView>(R.id.tv_location_address).text = booking.address

        // Set appointment details
        findViewById<TextView>(R.id.tv_appointment_date).text = booking.date
        findViewById<TextView>(R.id.tv_appointment_time).text = "${booking.time}, ${booking.timezone}"

        // Handle status-specific UI elements
        setupStatusSpecificViews()
        
        // Setup chat button
        setupChatButton()
    }

    private fun setupStatusSpecificViews() {
        when (booking.status) {
            BookingStatus.UPCOMING -> {
                // Show recommendation section
                findViewById<LinearLayout>(R.id.ll_recommendation).visibility = android.view.View.VISIBLE
                findViewById<TextView>(R.id.tv_recommendation_text).text = booking.recommendation
                
                // Set up "View more" click listener
                setupViewMoreClickListener()
                
                // Show cancel and reschedule buttons
                findViewById<TextView>(R.id.btn_cancel).visibility = android.view.View.VISIBLE
                findViewById<TextView>(R.id.btn_reschedule).visibility = android.view.View.VISIBLE
                
                // Set up button listeners
                findViewById<TextView>(R.id.btn_cancel).setOnClickListener {
                    showCancelBookingDialog()
                }
                findViewById<TextView>(R.id.btn_reschedule).setOnClickListener {
                    showRescheduleDialog()
                }
            }
            
            BookingStatus.COMPLETED -> {
                // Hide recommendation section
                findViewById<LinearLayout>(R.id.ll_recommendation).visibility = android.view.View.GONE
                
                // Hide cancel and reschedule buttons, show leave review functionality
                findViewById<TextView>(R.id.btn_cancel).visibility = android.view.View.GONE
                findViewById<TextView>(R.id.btn_reschedule).text = "LEAVE REVIEW"
                findViewById<TextView>(R.id.btn_reschedule).setOnClickListener {
                    // Navigate to ReviewActivity
                    val intent = Intent(this, ReviewActivity::class.java)
                    intent.putExtra("booking", booking)
                    startActivity(intent)
                }
            }
            
            BookingStatus.CANCELLED -> {
                // Hide recommendation section
                findViewById<LinearLayout>(R.id.ll_recommendation).visibility = android.view.View.GONE
                
                // Hide cancel button, show booking details functionality
                findViewById<TextView>(R.id.btn_cancel).visibility = android.view.View.GONE
                findViewById<TextView>(R.id.btn_reschedule).text = "GO TO BOOKING DETAILS"
                findViewById<TextView>(R.id.btn_reschedule).setOnClickListener {
                    // Handle booking details action
                }
            }
        }
    }

    private fun setupViewMoreClickListener() {
        // Find the "View more" text in the recommendation section
        val recommendationLayout = findViewById<LinearLayout>(R.id.ll_recommendation)
        val viewMoreText = recommendationLayout.findViewById<TextView>(R.id.tv_view_more)
        
        if (viewMoreText != null) {
            viewMoreText.setOnClickListener {
                val bottomSheet = RecommendationBottomSheetDialog.newInstance()
                bottomSheet.show(supportFragmentManager, "RecommendationBottomSheet")
            }
        }
    }

    private fun showCancelBookingDialog() {
        val bottomSheet = CancelBookingBottomSheetDialog.newInstance(booking.id) { reason ->
            // Handle cancel confirmation
            // You can add your cancel booking logic here
            // For now, just show a toast or handle the cancellation
            finish() // Close the detail screen after cancellation
        }
        bottomSheet.show(supportFragmentManager, "CancelBookingBottomSheet")
    }

    private fun showRescheduleDialog() {
        val bottomSheet = RescheduleBottomSheetDialog.newInstance { date, timeSlot ->
            // Handle reschedule confirmation
            // You can add your reschedule booking logic here
            // For now, just show a toast or handle the reschedule
            // Update the booking with new date and time
            finish() // Close the detail screen after reschedule
        }
        bottomSheet.show(supportFragmentManager, "RescheduleBottomSheet")
    }
    
    private fun setupChatButton() {
        findViewById<ImageView>(R.id.btn_chat).setOnClickListener {
            // Navigate to ChatActivity
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("booking", booking)
            startActivity(intent)
        }
    }
}
