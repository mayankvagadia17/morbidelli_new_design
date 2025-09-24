package com.morbidelli.morbidelli_design

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.morbidelli.morbidelli_design.adapter.ViewPagerAdapter
import com.morbidelli.morbidelli_design.fragment.ListFragment
import com.morbidelli.morbidelli_design.fragment.MapFragment
import com.morbidelli.morbidelli_design.model.LocationModel
class BookTestRideActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var mapFragment: MapFragment
    private lateinit var listFragment: ListFragment
    
    private lateinit var btnBack: ImageButton
    private lateinit var btnMapView: TextView
    private lateinit var btnListView: TextView
    private lateinit var btnBackStep: android.widget.Button
    private lateinit var btnNextStep: android.widget.Button
    
    private var isMapViewSelected = true
    private var selectedLocation: LocationModel? = null
    
    private val searchLocationRequestCode = 1001
    
    private val sampleLocations = listOf(
        LocationModel(
            id = 1,
            dealershipName = "Adventure Moto and services Official Dealership",
            address = "1234 Elm Avenue, Brooklyn, NY 11201, US",
            fromDate = "18.08.2025",
            latitude = 27.0844,  // Arunachal Pradesh
            longitude = 93.6053
        ),
        LocationModel(
            id = 2,
            dealershipName = "Morbidelli Northeast Service Center",
            address = "Imphal, Manipur, India",  
            fromDate = "20.08.2025",
            latitude = 24.8170,  // Manipur
            longitude = 93.9368
        ),
        LocationModel(
            id = 4,
            dealershipName = "Morbidelli Myanmar Dealership",
            address = "Yangon, Myanmar",
            fromDate = "22.08.2025",
            latitude = 16.8661,  // Yangon, Myanmar
            longitude = 96.1951
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_test_ride)
        
        initViews()
        setupViewPager()
        setupClickListeners()
    }
    
    private fun initViews() {
        viewPager = findViewById(R.id.view_pager)
        btnBack = findViewById(R.id.btn_back)
        btnMapView = findViewById(R.id.btn_map_view)
        btnListView = findViewById(R.id.btn_list_view)
        btnBackStep = findViewById(R.id.btn_back_step)
        btnNextStep = findViewById(R.id.btn_next_step)
    }
    
    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        
        // Get fragment references
        mapFragment = viewPagerAdapter.getMapFragment()
        listFragment = viewPagerAdapter.getListFragment()
        
        // Set up fragment listeners
        mapFragment.setOnLocationSelectedListener { location ->
            selectLocation(location)
            listFragment.updateLocationSelection(location)
            mapFragment.updateLocationSelection(location)
        }
        
        mapFragment.setOnSearchClickedListener {
            showSearchActivity()
        }
        
        mapFragment.setOnFullscreenClickedListener {
            // Handle fullscreen
            Toast.makeText(this, "Fullscreen mode", Toast.LENGTH_SHORT).show()
        }
        
        listFragment.setOnLocationSelectedListener { location ->
            selectLocation(location)
            mapFragment.moveMapToLocation(location)
            mapFragment.updateLocationSelection(location)
        }
        
        listFragment.setOnSearchClickedListener {
            showSearchActivity()
        }
        
        // Disable swipe between pages
        viewPager.isUserInputEnabled = false
    }
    
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            onBackPressed()
        }
        
        btnMapView.setOnClickListener {
            if (!isMapViewSelected) {
                toggleViewMode(true)
            }
        }
        
        btnListView.setOnClickListener {
            if (isMapViewSelected) {
                toggleViewMode(false)
            }
        }
        
        btnBackStep.setOnClickListener {
            onBackPressed()
        }
        
        btnNextStep.setOnClickListener {
            if (selectedLocation != null) {
                proceedToNextStep()
            } else {
                Toast.makeText(this, "Please select a location", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun toggleViewMode(isMapView: Boolean) {
        isMapViewSelected = isMapView
        
        if (isMapView) {
            // Update tab appearance
            btnMapView.setBackgroundResource(R.drawable.toggle_selected_background)
            btnMapView.setTextColor(ContextCompat.getColor(this, R.color.black))
            btnListView.setBackgroundResource(android.R.color.transparent)
            btnListView.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected_text))
            
            // Switch to map view
            viewPager.currentItem = 0
        } else {
            // Update tab appearance
            btnListView.setBackgroundResource(R.drawable.toggle_selected_background)
            btnListView.setTextColor(ContextCompat.getColor(this, R.color.black))
            btnMapView.setBackgroundResource(android.R.color.transparent)
            btnMapView.setTextColor(ContextCompat.getColor(this, R.color.tab_unselected_text))
            
            // Switch to list view
            viewPager.currentItem = 1
        }
    }
    
    private fun selectLocation(location: LocationModel) {
        selectedLocation = location
        Toast.makeText(this, "Selected: ${location.dealershipName}", Toast.LENGTH_SHORT).show()
    }
    
    private fun showSearchActivity() {
        val intent = Intent(this, SearchLocationActivity::class.java)
        intent.putExtra(SearchLocationActivity.EXTRA_LOCATIONS, ArrayList(sampleLocations))
        startActivityForResult(intent, searchLocationRequestCode)
    }
    
    private fun proceedToNextStep() {
        Toast.makeText(this, "Proceeding to step 3", Toast.LENGTH_SHORT).show()
        // Implement navigation to next step
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == searchLocationRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra(SearchLocationActivity.RESULT_SELECTED_LOCATION)?.let { location ->
                val selectedLocation = location as LocationModel
                selectLocation(selectedLocation)
                mapFragment.moveMapToLocation(selectedLocation)
                listFragment.updateLocationSelection(selectedLocation)
                mapFragment.updateLocationSelection(selectedLocation)
            }
        }
    }
}
