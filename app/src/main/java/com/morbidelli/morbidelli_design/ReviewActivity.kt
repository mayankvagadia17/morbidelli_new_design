package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.morbidelli.morbidelli_design.model.Booking

class ReviewActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var tvRatingDescription: TextView
    private lateinit var etReview: EditText
    private lateinit var tvCharacterCount: TextView
    private lateinit var btnSkip: Button
    private lateinit var btnSubmit: Button

    private var selectedRating = 0
    private var booking: Booking? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Get booking data from intent
        booking = intent.getSerializableExtra("booking") as? Booking

        setupViews()
        setupRatingStars()
        setupReviewText()
        setupButtonListeners()
        updateRatingDescription()
    }

    private fun setupViews() {
        btnBack = findViewById(R.id.btn_back)
        star1 = findViewById(R.id.star_1)
        star2 = findViewById(R.id.star_2)
        star3 = findViewById(R.id.star_3)
        star4 = findViewById(R.id.star_4)
        star5 = findViewById(R.id.star_5)
        tvRatingDescription = findViewById(R.id.tv_rating_description)
        etReview = findViewById(R.id.et_review)
        tvCharacterCount = findViewById(R.id.tv_character_count)
        btnSkip = findViewById(R.id.btn_skip)
        btnSubmit = findViewById(R.id.btn_submit)
    }

    private fun setupRatingStars() {
        val stars = listOf(star1, star2, star3, star4, star5)
        
        stars.forEachIndexed { index, star ->
            star.setOnClickListener {
                selectedRating = index + 1
                updateStarDisplay()
                updateRatingDescription()
                updateSubmitButton()
            }
        }
    }

    private fun updateStarDisplay() {
        val stars = listOf(star1, star2, star3, star4, star5)
        
        stars.forEachIndexed { index, star ->
            if (index < selectedRating) {
                star.setImageResource(R.drawable.ic_star_filled)
            } else {
                star.setImageResource(R.drawable.ic_star_empty)
            }
        }
    }

    private fun updateRatingDescription() {
        val descriptions = listOf(
            "Tap a star to rate",
            "Poor",
            "Fair", 
            "Good",
            "Very Good",
            "Excellent"
        )
        
        tvRatingDescription.text = descriptions[selectedRating]
        
        // Update text color based on rating
        when (selectedRating) {
            0 -> tvRatingDescription.setTextColor(resources.getColor(android.R.color.darker_gray, null))
            1, 2 -> tvRatingDescription.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
            3 -> tvRatingDescription.setTextColor(resources.getColor(android.R.color.holo_orange_dark, null))
            4, 5 -> tvRatingDescription.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
        }
    }

    private fun setupReviewText() {
        etReview.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentLength = s?.length ?: 0
                tvCharacterCount.text = "$currentLength/500"
                
                // Change color if approaching limit
                if (currentLength > 450) {
                    tvCharacterCount.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                } else {
                    tvCharacterCount.setTextColor(resources.getColor(android.R.color.darker_gray, null))
                }
                
                updateSubmitButton()
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun updateSubmitButton() {
        val hasRating = selectedRating > 0
        val hasReview = etReview.text.toString().trim().isNotEmpty()
        
        btnSubmit.isEnabled = hasRating || hasReview
        btnSubmit.alpha = if (btnSubmit.isEnabled) 1.0f else 0.5f
    }

    private fun setupButtonListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnSkip.setOnClickListener {
            // Just close the activity without saving
            finish()
        }

        btnSubmit.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val reviewText = etReview.text.toString().trim()
        
        // Validate review length
        if (reviewText.length > 500) {
            // Show error or truncate
            return
        }
        
        // Here you would typically save the review to your backend
        // For now, we'll just show a toast and close
        android.widget.Toast.makeText(
            this,
            "Review submitted successfully!",
            android.widget.Toast.LENGTH_SHORT
        ).show()
        
        finish()
    }
}
