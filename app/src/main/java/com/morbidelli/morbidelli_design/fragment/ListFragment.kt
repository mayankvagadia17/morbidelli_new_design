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
import com.morbidelli.morbidelli_design.model.LocationModel

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
                // Handle view more click
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
