package com.morbidelli.morbidelli_design

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.LocationAdapter
import com.morbidelli.morbidelli_design.model.LocationModel

class SearchLocationActivity : AppCompatActivity() {

    private lateinit var etSearch: EditText
    private lateinit var btnClose: ImageView
    private lateinit var tabAdventureMoto: TextView
    private lateinit var tabMorbidelli: TextView
    private lateinit var rvSearchResults: RecyclerView
    private lateinit var searchAdapter: LocationAdapter
    
    private var selectedFilter = "Adventure Moto"
    private var allLocations = listOf<LocationModel>()
    private var filteredLocations = mutableListOf<LocationModel>()

    companion object {
        const val EXTRA_LOCATIONS = "extra_locations"
        const val RESULT_SELECTED_LOCATION = "result_selected_location"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_location)
        
        // Get locations from intent
        allLocations = intent.getSerializableExtra(EXTRA_LOCATIONS) as? List<LocationModel> ?: emptyList()
        
        initViews()
        setupClickListeners()
        setupSearch()
        setupRecyclerView()
        updateFilteredLocations()
    }
    
    private fun initViews() {
        etSearch = findViewById(R.id.et_search)
        btnClose = findViewById(R.id.btn_close)
        tabAdventureMoto = findViewById(R.id.tab_adventure_moto)
        tabMorbidelli = findViewById(R.id.tab_morbidelli)
        rvSearchResults = findViewById(R.id.rv_search_results)
    }
    
    private fun setupClickListeners() {
        btnClose.setOnClickListener {
            finish()
        }
        
        tabAdventureMoto.setOnClickListener {
            selectFilter("Adventure Moto")
        }
        
        tabMorbidelli.setOnClickListener {
            selectFilter("Morbidelli")
        }
    }
    
    private fun setupSearch() {
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                performSearch(s.toString())
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
        
        // Auto focus on search input
        etSearch.requestFocus()
    }
    
    private fun setupRecyclerView() {
        searchAdapter = LocationAdapter(
            locations = filteredLocations,
            onLocationClick = { location ->
                selectLocation(location)
            },
            onViewMoreClick = { location ->
                selectLocation(location)
            }
        )
        
        rvSearchResults.apply {
            layoutManager = LinearLayoutManager(this@SearchLocationActivity)
            adapter = searchAdapter
        }
    }
    
    private fun selectFilter(filter: String) {
        selectedFilter = filter
        
        // Update tab appearances
        if (filter == "Adventure Moto") {
            tabAdventureMoto.setBackgroundResource(R.drawable.filter_tab_selected)
            tabAdventureMoto.setTextColor(ContextCompat.getColor(this, R.color.black))
            
            tabMorbidelli.setBackgroundResource(R.drawable.filter_tab_unselected)
            tabMorbidelli.setTextColor(ContextCompat.getColor(this, R.color.filter_unselected_text))
        } else {
            tabMorbidelli.setBackgroundResource(R.drawable.filter_tab_selected)
            tabMorbidelli.setTextColor(ContextCompat.getColor(this, R.color.black))
            
            tabAdventureMoto.setBackgroundResource(R.drawable.filter_tab_unselected)
            tabAdventureMoto.setTextColor(ContextCompat.getColor(this, R.color.filter_unselected_text))
        }
        
        updateFilteredLocations()
        performSearch(etSearch.text.toString())
    }
    
    private fun updateFilteredLocations() {
        filteredLocations.clear()
        
        // Filter locations based on selected filter
        // For now, we'll just show all locations regardless of filter
        // You can implement actual filtering logic based on your data structure
        filteredLocations.addAll(allLocations)
    }
    
    private fun performSearch(query: String) {
        if (query.isEmpty()) {
            rvSearchResults.visibility = android.view.View.GONE
            findViewById<android.view.View>(R.id.empty_state).visibility = android.view.View.VISIBLE
            return
        }
        
        // Filter locations based on search query
        val searchResults = filteredLocations.filter { location ->
            location.dealershipName.contains(query, ignoreCase = true) ||
            location.address.contains(query, ignoreCase = true)
        }
        
        if (searchResults.isNotEmpty()) {
            // Update adapter with search results
            searchAdapter.updateLocations(searchResults)
            rvSearchResults.visibility = android.view.View.VISIBLE
            findViewById<android.view.View>(R.id.empty_state).visibility = android.view.View.GONE
        } else {
            rvSearchResults.visibility = android.view.View.GONE
            findViewById<android.view.View>(R.id.empty_state).visibility = android.view.View.VISIBLE
        }
    }
    
    private fun selectLocation(location: LocationModel) {
        // Return selected location to calling activity
        val resultIntent = Intent().apply {
            putExtra(RESULT_SELECTED_LOCATION, location)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
    
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
