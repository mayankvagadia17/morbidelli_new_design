package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.FeatureAdapter
import com.morbidelli.morbidelli_design.adapter.GalleryAdapter
import com.morbidelli.morbidelli_design.adapter.PodcastAdapter
import com.morbidelli.morbidelli_design.adapter.TechSpecAdapter
import com.morbidelli.morbidelli_design.model.Feature
import com.morbidelli.morbidelli_design.model.GalleryItem
import com.morbidelli.morbidelli_design.model.GalleryItemType
import com.morbidelli.morbidelli_design.model.PodcastItem
import com.morbidelli.morbidelli_design.model.TechSpecCategory
import com.morbidelli.morbidelli_design.R

class ModelDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_VEHICLE_NAME = "model_name"
        const val EXTRA_VEHICLE_IMAGE = "vehicle_image"
        const val EXTRA_VEHICLE_CATEGORY = "vehicle_category"
    }

    private lateinit var tvMain: TextView
    private lateinit var tvIntro: TextView
    private lateinit var tvPodcasts: TextView
    private lateinit var tvTopFeatures: TextView
    private lateinit var btnBookTestRide: Button
    private lateinit var ivBack: ImageView
    private lateinit var tvModelName: TextView
    
    // Hero section
    private lateinit var ivHeroImage: ImageView
    private lateinit var tvTrailDreams: TextView
    private lateinit var tvModelNameHero: TextView
    private lateinit var rvThumbnails: RecyclerView
    
    // Specifications
    private lateinit var tvPriceFrom: TextView
    private lateinit var tvPower: TextView
    private lateinit var tvCurbWeight: TextView
    private lateinit var tvMaxTorque: TextView
    
    // Introduction section
    private lateinit var tvIntroTitle: TextView
    private lateinit var tvIntroDescription: TextView
    
    // Podcasts section
    private lateinit var rvPodcasts: RecyclerView
    private lateinit var podcastAdapter: PodcastAdapter
    private lateinit var featureAdapter: FeatureAdapter
    
    // Top Features section
    private lateinit var rvTopFeatures: RecyclerView
    
    // Gallery section
    private lateinit var rvGallery: RecyclerView
    private lateinit var galleryAdapter: GalleryAdapter
    
    // Tech Specs section
    private lateinit var rvTechSpecs: RecyclerView
    private lateinit var techSpecAdapter: TechSpecAdapter
    private lateinit var btnCollapseAll: Button
    private lateinit var btnExpandAll: Button
    
    private var selectedTab = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_details)

        setupViews()
        setupNavigationTabs()
        setupRecyclerViews()
        setupClickListeners()
        loadData()
    }

    private fun setupViews() {
        // Navigation
        tvMain = findViewById(R.id.tv_main)
        tvIntro = findViewById(R.id.tv_intro)
        tvPodcasts = findViewById(R.id.tv_podcasts)
        tvTopFeatures = findViewById(R.id.tv_top_features)
        btnBookTestRide = findViewById(R.id.btn_book_test_ride)
        ivBack = findViewById(R.id.iv_back)
        tvModelName = findViewById(R.id.tv_model_name)
        
        // Hero section
        ivHeroImage = findViewById(R.id.iv_hero_image)
        tvTrailDreams = findViewById(R.id.tv_trail_dreams)
        tvModelNameHero = findViewById(R.id.tv_model_name_hero)
        rvThumbnails = findViewById(R.id.rv_thumbnails)
        
        // Specifications
        tvPriceFrom = findViewById(R.id.tv_price_from)
        tvPower = findViewById(R.id.tv_power)
        tvCurbWeight = findViewById(R.id.tv_curb_weight)
        tvMaxTorque = findViewById(R.id.tv_max_torque)
        
        // Introduction
        tvIntroTitle = findViewById(R.id.tv_intro_title)
        tvIntroDescription = findViewById(R.id.tv_intro_description)
        
        // Podcasts
        rvPodcasts = findViewById(R.id.rv_podcasts)
        
        // Top Features
        rvTopFeatures = findViewById(R.id.rv_top_features)
        
        // Gallery
        rvGallery = findViewById(R.id.rv_gallery)
        
        // Tech Specs
        rvTechSpecs = findViewById(R.id.rv_tech_specs)
        btnCollapseAll = findViewById(R.id.btn_collapse_all)
        btnExpandAll = findViewById(R.id.btn_expand_all)
    }

    private fun setupNavigationTabs() {
        updateTabSelection()
    }

    private fun setupRecyclerViews() {
        // Thumbnails
        rvThumbnails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        
        // Podcasts
        podcastAdapter = PodcastAdapter()
        rvPodcasts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvPodcasts.adapter = podcastAdapter
        
        // Gallery
        galleryAdapter = GalleryAdapter()
        rvGallery.layoutManager = GridLayoutManager(this, 2)
        rvGallery.adapter = galleryAdapter
        
        // Tech Specs
        techSpecAdapter = TechSpecAdapter()
        rvTechSpecs.layoutManager = LinearLayoutManager(this)
        rvTechSpecs.adapter = techSpecAdapter

        // Setup Top Features RecyclerView
        featureAdapter = FeatureAdapter()
        rvTopFeatures.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTopFeatures.adapter = featureAdapter
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            finish()
        }
        
        btnBookTestRide.setOnClickListener {
            val intent = Intent(this, BookTestRideActivity::class.java)
            intent.putExtra("model_name", tvModelName.text.toString())
            startActivity(intent)
        }
        
        tvMain.setOnClickListener { selectTab("Main") }
        tvIntro.setOnClickListener { selectTab("Intro") }
        tvPodcasts.setOnClickListener { selectTab("Podcasts") }
        tvTopFeatures.setOnClickListener { selectTab("Top Features") }
        
        btnCollapseAll.setOnClickListener {
            techSpecAdapter.collapseAll()
        }
        
        btnExpandAll.setOnClickListener {
            techSpecAdapter.expandAll()
        }
    }

    private fun selectTab(tab: String) {
        selectedTab = tab
        updateTabSelection()
        updateContentVisibility()
    }

    private fun updateTabSelection() {
        // Reset all tabs
        tvMain.setTextColor(getColor(R.color.text_secondary))
        tvIntro.setTextColor(getColor(R.color.text_secondary))
        tvPodcasts.setTextColor(getColor(R.color.text_secondary))
        tvTopFeatures.setTextColor(getColor(R.color.text_secondary))
        
        // Remove underlines
        tvMain.setBackgroundResource(0)
        tvIntro.setBackgroundResource(0)
        tvPodcasts.setBackgroundResource(0)
        tvTopFeatures.setBackgroundResource(0)
        
        // Highlight selected tab
        when (selectedTab) {
            "Main" -> {
                tvMain.setTextColor(getColor(R.color.black))
                tvMain.setBackgroundResource(R.drawable.tab_underline)
            }
            "Intro" -> {
                tvIntro.setTextColor(getColor(R.color.black))
                tvIntro.setBackgroundResource(R.drawable.tab_underline)
            }
            "Podcasts" -> {
                tvPodcasts.setTextColor(getColor(R.color.black))
                tvPodcasts.setBackgroundResource(R.drawable.tab_underline)
            }
            "Top Features" -> {
                tvTopFeatures.setTextColor(getColor(R.color.black))
                tvTopFeatures.setBackgroundResource(R.drawable.tab_underline)
            }
        }
    }

    private fun updateContentVisibility() {
        // This would control which sections are visible based on selected tab
        // For now, all sections are visible
    }

    private fun loadData() {
        // Set model name from intent
        val modelName = intent.getStringExtra(EXTRA_VEHICLE_NAME) ?: "T352X"
        val vehicleImage = intent.getIntExtra(EXTRA_VEHICLE_IMAGE, R.drawable.ic_launcher_foreground)
        val vehicleCategory = intent.getStringExtra(EXTRA_VEHICLE_CATEGORY) ?: "Trail"
        
        tvModelName.text = modelName
        tvModelNameHero.text = modelName
        ivHeroImage.setImageResource(vehicleImage)
        
        // Load podcast data
        val podcasts = getPodcastData()
        podcastAdapter.updateData(podcasts)
        
        // Load gallery data
        val galleryItems = getGalleryData()
        galleryAdapter.updateData(galleryItems)
        
        // Load tech specs data
        val techSpecs = getTechSpecData()
        techSpecAdapter.updateData(techSpecs)
        
        // Load top features
        val features = getFeatureData()
        featureAdapter.updateData(features)
    }

    private fun getPodcastData(): List<PodcastItem> {
        return listOf(
            PodcastItem(
                id = 1,
                title = "Morbidelli T502X e T352X - prova - in sella alle Morbidelli Enduro che sfidano le Benelli",
                thumbnail = R.drawable.ic_launcher_foreground,
                source = "RED live",
                timeAgo = "4 days ago",
                overlayText = "BICILINDRICHE AGGUERRITE"
            ),
            PodcastItem(
                id = 2,
                title = "Morbid econom strada",
                thumbnail = R.drawable.ic_launcher_foreground,
                source = "MORBIDELLI",
                timeAgo = "1 week ago",
                overlayText = "T3"
            )
        )
    }

    private fun getGalleryData(): List<GalleryItem> {
        return listOf(
            GalleryItem(id = 1, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 2, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 3, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 4, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 5, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 6, type = GalleryItemType.IMAGE, resourceId = R.drawable.ic_launcher_foreground),
            GalleryItem(id = 7, type = GalleryItemType.VIDEO, resourceId = R.drawable.ic_launcher_foreground, videoDuration = "0:15")
        )
    }

    private fun getTechSpecData(): List<TechSpecCategory> {
        return listOf(
            TechSpecCategory(
                id = 1,
                title = "Engine",
                isExpanded = true,
                specifications = listOf(
                    "Displacement (cc)" to "149",
                    "Type" to "1-cylinder/4-stroke/2-valve",
                    "Bore x Stroke (mm)" to "ø62×49.5",
                    "Power" to "8.4kW at 8000 r/min",
                    "Max. torque" to "11.1N-m/6000 r/min",
                    "Compression ratio" to "9.3:1",
                    "Fuel system" to "Carburetor",
                    "Valve train" to "OHV",
                    "Ignition" to "CDI",
                    "Starter" to "Electric & Kick",
                    "Lubrication system" to "Pressure splash lubrication",
                    "Cooling system" to "Air-cooled"
                )
            ),
            TechSpecCategory(
                id = 2,
                title = "Transmission",
                isExpanded = false,
                specifications = listOf(
                    "Gearbox" to "5-speed",
                    "Clutch" to "Wet multi-plate",
                    "Final drive" to "Chain"
                )
            ),
            TechSpecCategory(
                id = 3,
                title = "Chassis",
                isExpanded = false,
                specifications = listOf(
                    "Frame" to "Steel tubular",
                    "Front suspension" to "Telescopic fork",
                    "Rear suspension" to "Monoshock"
                )
            ),
            TechSpecCategory(
                id = 4,
                title = "Dimensions and weights",
                isExpanded = false,
                specifications = listOf(
                    "Length" to "2,100 mm",
                    "Width" to "800 mm",
                    "Height" to "1,200 mm",
                    "Wheelbase" to "1,400 mm",
                    "Seat height" to "850 mm",
                    "Ground clearance" to "200 mm",
                    "Fuel tank capacity" to "18 L",
                    "Curb weight" to "151 kg"
                )
            ),
            TechSpecCategory(
                id = 5,
                title = "Connectivity and Security",
                isExpanded = false,
                specifications = listOf(
                    "Security" to "Immobilizer",
                    "Connectivity" to "Bluetooth"
                )
            ),
            TechSpecCategory(
                id = 6,
                title = "Assistive and Control Features",
                isExpanded = false,
                specifications = listOf(
                    "ABS" to "Bosch ABS with Off-road mode",
                    "Traction control" to "Not available"
                )
            ),
            TechSpecCategory(
                id = 7,
                title = "Multimedia & comfort features",
                isExpanded = false,
                specifications = listOf(
                    "Display" to "Digital",
                    "USB port" to "Available"
                )
            ),
            TechSpecCategory(
                id = 8,
                title = "Lighting configuration",
                isExpanded = false,
                specifications = listOf(
                    "Headlight" to "LED",
                    "Taillight" to "LED",
                    "Turn signals" to "LED"
                )
            ),
            TechSpecCategory(
                id = 9,
                title = "Active/Passive safety features",
                isExpanded = false,
                specifications = listOf(
                    "ABS" to "Standard",
                    "Traction control" to "Not available"
                )
            )
        )
    }

    private fun getFeatureData(): List<Feature> {
        return listOf(
            Feature(
                id = 1,
                title = "LED Lighting",
                description = "Advanced LED headlight system for superior visibility",
                iconResId = R.drawable.ic_launcher_foreground
            ),
            Feature(
                id = 2,
                title = "ABS Brakes",
                description = "Anti-lock braking system for enhanced safety",
                iconResId = R.drawable.ic_launcher_foreground
            ),
            Feature(
                id = 3,
                title = "Digital Display",
                description = "Modern digital instrument cluster with full information",
                iconResId = R.drawable.ic_launcher_foreground
            ),
            Feature(
                id = 4,
                title = "Sport Mode",
                description = "Multiple riding modes for different conditions",
                iconResId = R.drawable.ic_launcher_foreground
            ),
            Feature(
                id = 5,
                title = "Lightweight Frame",
                description = "High-strength aluminum frame for optimal performance",
                iconResId = R.drawable.ic_launcher_foreground
            ),
            Feature(
                id = 6,
                title = "Premium Suspension",
                description = "Adjustable suspension system for comfort and control",
                iconResId = R.drawable.ic_launcher_foreground
            )
        )
    }
}
