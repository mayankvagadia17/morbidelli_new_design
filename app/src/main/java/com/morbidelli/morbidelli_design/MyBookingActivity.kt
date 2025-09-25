package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.morbidelli.morbidelli_design.adapter.BookingAdapter
import com.morbidelli.morbidelli_design.model.Booking
import com.morbidelli.morbidelli_design.model.BookingStatus

class MyBookingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: BookingAdapter

    private val allBookings = mutableListOf<Booking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        setupToolbar()
        setupToolbarAndTabs()
        setupRecycler()
        seedData()
        applyFilter(BookingStatus.UPCOMING)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbarAndTabs() {
        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_upcoming))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_completed))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_cancelled))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> applyFilter(BookingStatus.UPCOMING)
                    1 -> applyFilter(BookingStatus.COMPLETED)
                    2 -> applyFilter(BookingStatus.CANCELLED)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupRecycler() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BookingAdapter(emptyList(), 
            onRescheduleClick = { booking ->
                // TODO: Hook your reschedule action
            }, 
            onCancelClick = { booking ->
                // TODO: Hook your cancel action
            },
            onItemClick = { booking ->
                val intent = Intent(this, BookingDetailActivity::class.java)
                intent.putExtra("booking", booking)
                startActivity(intent)
            }
        )
        recyclerView.adapter = adapter

        // Close open menus when scrolling
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    adapter.closeMenus()
                }
            }
        })
    }

    private fun seedData() {
        allBookings.clear()
        
        // Upcoming bookings
        allBookings.add(
            Booking(
                id = "TR-48291",
                date = "Oct 04, 2023",
                time = "10:00",
                vehicleModel = "C1002V",
                vehicleCode = "TR-48291",
                companyName = "Oakley Motorcycles",
                address = getString(R.string.via_fabio_severo),
                status = BookingStatus.UPCOMING,
                hasRescheduleOption = true,
                hasCancelOption = true
            )
        )
        
        allBookings.add(
            Booking(
                id = "TR-48292",
                date = "Wednesday, August 13, 2025",
                time = "7:00 am - 7:30 am",
                vehicleModel = "Trail",
                vehicleCode = "C1002V",
                companyName = "Oakley Motorcycles",
                address = "Via Fabio Severo 34, Trieste, Trieste 34127",
                status = BookingStatus.UPCOMING,
                hasRescheduleOption = true,
                hasCancelOption = true,
                contactPerson = "Matias Johnson",
                contactEmail = "matiasjohnson@examplemail.com",
                transmission = "6-speed manual",
                color = "Black",
                timezone = "GMT+8",
                recommendation = "Be sure to bring the required documents and arrive on time."
            )
        )
        
        // Completed bookings
        allBookings.add(
            Booking(
                id = "TR-48293",
                date = "Monday, July 15, 2025",
                time = "2:00 pm - 2:30 pm",
                vehicleModel = "Trail",
                vehicleCode = "C1002V",
                companyName = "Oakley Motorcycles",
                address = "Via Fabio Severo 34, Trieste, Trieste 34127",
                status = BookingStatus.COMPLETED,
                hasRescheduleOption = false,
                hasCancelOption = false,
                contactPerson = "Matias Johnson",
                contactEmail = "matiasjohnson@examplemail.com",
                transmission = "6-speed manual",
                color = "Black",
                timezone = "GMT+8"
            )
        )
        
        // Cancelled bookings
        allBookings.add(
            Booking(
                id = "TR-48294",
                date = "Friday, June 20, 2025",
                time = "11:00 am - 11:30 am",
                vehicleModel = "Trail",
                vehicleCode = "C1002V",
                companyName = "Oakley Motorcycles",
                address = "Via Fabio Severo 34, Trieste, Trieste 34127",
                status = BookingStatus.CANCELLED,
                hasRescheduleOption = false,
                hasCancelOption = false,
                contactPerson = "Matias Johnson",
                contactEmail = "matiasjohnson@examplemail.com",
                transmission = "6-speed manual",
                color = "Black",
                timezone = "GMT+8"
            )
        )
    }

    private fun applyFilter(status: BookingStatus) {
        val filtered = allBookings.filter { it.status == status }
        adapter.updateBookings(filtered)
    }
}
