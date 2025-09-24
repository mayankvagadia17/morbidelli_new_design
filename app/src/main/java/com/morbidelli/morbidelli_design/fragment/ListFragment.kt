package com.morbidelli.morbidelli_design.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.adapter.LocationListAdapter
import com.morbidelli.morbidelli_design.model.ContactInfo
import com.morbidelli.morbidelli_design.model.LocationModel
import com.morbidelli.morbidelli_design.model.WorkingHours

class ListFragment : Fragment() {

    private lateinit var rvLocationsList: RecyclerView
    private lateinit var searchBarContainer: View
    private lateinit var locationListAdapter: LocationListAdapter

    private var onLocationSelected: ((LocationModel) -> Unit)? = null
    private var onSearchClicked: (() -> Unit)? = null

    private val sampleLocations = listOf(
        LocationModel(
            id = 1,
            dealershipName = "Adventure Moto and services Official Dealership",
            address = "Via Fabio Severo 34, Trieste, Trieste 34127",
            fromDate = "18.08.2025",
            latitude = 27.0844,  // Arunachal Pradesh
            longitude = 93.6053,
            availableMotorbikes = 1,
            workingHours = WorkingHours(
                weekdays = "9:00-12:30 / 15:00-19:00 Monday morning closed",
                weekend = "Saturday: 9:00-12:30 / 15:00-18:00"
            ),
            contactInfo = ContactInfo(
                phoneNumber = "(808) 555-0111",
                email = "giupponimoto@gmail.com"
            )
        ),
        LocationModel(
            id = 2,
            dealershipName = "Morbidelli Northeast Service Center",
            address = "Imphal, Manipur, India",  
            fromDate = "20.08.2025",
            latitude = 24.8170,  // Manipur
            longitude = 93.9368,
            availableMotorbikes = 2,
            workingHours = WorkingHours(
                weekdays = "9:00-18:00",
                weekend = "Saturday: 9:00-17:00"
            ),
            contactInfo = ContactInfo(
                phoneNumber = "(555) 123-4567",
                email = "northeast@morbidelli.com"
            )
        ),
        LocationModel(
            id = 4,
            dealershipName = "Morbidelli Myanmar Dealership",
            address = "Yangon, Myanmar",
            fromDate = "22.08.2025",
            latitude = 16.8661,  // Yangon, Myanmar
            longitude = 96.1951,
            availableMotorbikes = 1,
            workingHours = WorkingHours(
                weekdays = "8:00-17:00",
                weekend = "Saturday: 8:00-16:00"
            ),
            contactInfo = ContactInfo(
                phoneNumber = "+95 1 234 567",
                email = "myanmar@morbidelli.com"
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupRecyclerView()
        setupClickListeners()
    }

    private fun initViews(view: View) {
        rvLocationsList = view.findViewById(R.id.rv_locations_list)
        searchBarContainer = view.findViewById(R.id.search_bar_container)
    }

    private fun setupRecyclerView() {
        locationListAdapter = LocationListAdapter(
            locations = sampleLocations.toMutableList(),
            onLocationClick = { location ->
                onLocationSelected?.invoke(location)
            },
            onViewMoreClick = { location ->
                // Handle view more click - now handled internally
            },
            onExternalLinkClick = { location ->
                // Handle external link click
            }
        )
        
        rvLocationsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = locationListAdapter
        }
    }

    private fun setupClickListeners() {
        searchBarContainer.setOnClickListener {
            onSearchClicked?.invoke()
        }
    }

    fun setOnLocationSelectedListener(listener: (LocationModel) -> Unit) {
        onLocationSelected = listener
    }

    fun setOnSearchClickedListener(listener: () -> Unit) {
        onSearchClicked = listener
    }

    fun updateLocationSelection(location: LocationModel) {
        locationListAdapter.setSelectedLocation(location.id)
    }
}
