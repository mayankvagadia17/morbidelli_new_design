package com.morbidelli.morbidelli_design.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.LocationModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var btnZoomIn: ImageButton
    private lateinit var btnZoomOut: ImageButton
    private lateinit var btnMyLocation: ImageButton
    private lateinit var btnSearch: ImageButton
    private lateinit var btnFullscreen: ImageButton

    private var onLocationSelected: ((LocationModel) -> Unit)? = null
    private var onSearchClicked: (() -> Unit)? = null
    private var onFullscreenClicked: (() -> Unit)? = null

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
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        setupMapView(savedInstanceState)
        setupClickListeners()
    }

    private fun initViews(view: View) {
        mapView = view.findViewById(R.id.map_view)
        btnZoomIn = view.findViewById(R.id.btn_zoom_in)
        btnZoomOut = view.findViewById(R.id.btn_zoom_out)
        btnMyLocation = view.findViewById(R.id.btn_my_location)
        btnSearch = view.findViewById(R.id.btn_search)
        btnFullscreen = view.findViewById(R.id.btn_fullscreen)
    }

    private fun setupMapView(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun setupClickListeners() {
        btnZoomIn.setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn())
            }
        }

        btnZoomOut.setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut())
            }
        }

        btnMyLocation.setOnClickListener {
            enableMyLocation()
        }

        btnSearch.setOnClickListener {
            onSearchClicked?.invoke()
        }

        btnFullscreen.setOnClickListener {
            onFullscreenClicked?.invoke()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        
        // Configure map settings
        googleMap.uiSettings.apply {
            isZoomControlsEnabled = false
            isMyLocationButtonEnabled = false
            isMapToolbarEnabled = false
        }
        
        // Add numbered markers for sample locations
        sampleLocations.forEach { location ->
            val markerIcon = createNumberedMarkerIcon(location.id.toString())
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .title(location.dealershipName)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
            )
            marker?.tag = location
        }
        
        // Move camera to Northeast India/Myanmar region
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(24.0, 94.0),  // Center on Northeast India/Myanmar region
                6f  // Zoom level to show the whole region
            )
        )
        
        // Set marker click listener
        googleMap.setOnMarkerClickListener { marker ->
            val location = marker.tag as? LocationModel
            location?.let {
                onLocationSelected?.invoke(it)
            }
            true
        }
        
        enableMyLocation()
    }

    private fun createNumberedMarkerIcon(number: String): Bitmap {
        val size = 96  // Size in pixels
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Draw blue circle
        val circlePaint = Paint().apply {
            color = Color.parseColor("#2196F3")
            isAntiAlias = true
        }
        val radius = size / 2f - 4f
        canvas.drawCircle(size / 2f, size / 2f, radius, circlePaint)
        
        // Draw white border
        val borderPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.STROKE
            strokeWidth = 4f
            isAntiAlias = true
        }
        canvas.drawCircle(size / 2f, size / 2f, radius, borderPaint)
        
        // Draw white number text
        val textPaint = Paint().apply {
            color = Color.WHITE
            textSize = 32f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            isFakeBoldText = true
        }
        
        val textBounds = Rect()
        textPaint.getTextBounds(number, 0, number.length, textBounds)
        val textY = (size / 2f) + (textBounds.height() / 2f)
        canvas.drawText(number, size / 2f, textY, textPaint)
        
        return bitmap
    }

    private fun enableMyLocation() {
        if (::googleMap.isInitialized) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = false
            } else {
                requestLocationPermission()
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            1000
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == 1000) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            }
        }
    }

    fun setOnLocationSelectedListener(listener: (LocationModel) -> Unit) {
        onLocationSelected = listener
    }

    fun setOnSearchClickedListener(listener: () -> Unit) {
        onSearchClicked = listener
    }

    fun setOnFullscreenClickedListener(listener: () -> Unit) {
        onFullscreenClicked = listener
    }

    fun moveMapToLocation(location: LocationModel) {
        if (::googleMap.isInitialized) {
            val position = LatLng(location.latitude, location.longitude)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14f))
        }
    }

    // MapView lifecycle methods
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
