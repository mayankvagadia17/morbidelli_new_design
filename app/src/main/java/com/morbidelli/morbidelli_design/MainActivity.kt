package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.RideAdapter
import com.morbidelli.morbidelli_design.model.RideModel

class MainActivity : AppCompatActivity() {

    private lateinit var cardUpcoming: CardView
    private lateinit var cardCompleted: CardView
    private lateinit var cardCancelled: CardView
    private lateinit var btnBookTestRide: Button
    private lateinit var tvUpcomingCount: TextView
    private lateinit var tvCompletedCount: TextView
    private lateinit var tvCancelledCount: TextView

    private lateinit var llBookingCards: LinearLayout
    private lateinit var llEmptyState: ScrollView
    private lateinit var rvRides: RecyclerView
    private lateinit var rideAdapter: RideAdapter

    private var isShowingEmptyState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupClickListeners()
        setupRidesRecyclerView()
        updateCounts()
    }

    private fun setupViews() {
        cardUpcoming = findViewById(R.id.card_upcoming)
        cardCompleted = findViewById(R.id.card_completed)
        cardCancelled = findViewById(R.id.card_cancelled)
        btnBookTestRide = findViewById(R.id.btn_book_test_ride)
        tvUpcomingCount = findViewById(R.id.tv_upcoming_count)
        tvCompletedCount = findViewById(R.id.tv_completed_count)
        tvCancelledCount = findViewById(R.id.tv_cancelled_count)

        llBookingCards = findViewById(R.id.ll_booking)
        llEmptyState = findViewById(R.id.ll_empty_state)
        rvRides = findViewById(R.id.rv_rides)
    }

    private fun setupClickListeners() {
        cardUpcoming.setOnClickListener {
            startActivity(Intent(this, TestRideActivity::class.java))
        }

        cardCompleted.setOnClickListener {
            startActivity(Intent(this, MyBookingActivity::class.java))
        }

        cardCancelled.setOnClickListener {
            startActivity(Intent(this, ModelSliderActivity::class.java))
        }

        btnBookTestRide.setOnClickListener {
            toggleEmptyState()
        }
    }

    private fun setupRidesRecyclerView() {
        val rideList = listOf(
            RideModel(
                id = 1,
                bikeName = getString(R.string.n300_model),
                bikeImage = R.drawable.ic_bike_n300,
                powerSpec = "Power: 295/10000 HP",
                weightSpec = "Curb Weight: 161 KG"
            ),
            RideModel(
                id = 2,
                bikeName = getString(R.string.n250r_model),
                bikeImage = R.drawable.ic_bike_n250r,
                powerSpec = "Power: 25.75/9250 HP",
                weightSpec = "Curb Weight: 151 KG"
            ),
            RideModel(
                id = 3,
                bikeName = "N400 Sport",
                bikeImage = R.drawable.ic_bike_n300,
                powerSpec = "Power: 400/11000 HP",
                weightSpec = "Curb Weight: 175 KG"
            )
        )

        rideAdapter = RideAdapter(rideList) { selectedRide ->
            onRideSelected(selectedRide)
        }

        rvRides.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = rideAdapter
            setHasFixedSize(true)
        }
    }

    private fun onRideSelected(ride: RideModel) {
        startActivity(
            Intent(this, TestRideActivity::class.java)
        )
    }

    private fun updateCounts() {
        tvUpcomingCount.text = "4"
        tvCompletedCount.text = "18"
        tvCancelledCount.text = "7"
    }

    private fun toggleEmptyState() {
        if (isShowingEmptyState) {
            llBookingCards.visibility = View.VISIBLE
            llEmptyState.visibility = View.GONE
            isShowingEmptyState = false
        } else {
            llBookingCards.visibility = View.GONE
            llEmptyState.visibility = View.VISIBLE
            isShowingEmptyState = true
        }
    }
}
