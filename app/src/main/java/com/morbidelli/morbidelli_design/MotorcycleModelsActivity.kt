package com.morbidelli.morbidelli_design

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.MotorcycleModelAdapter
import com.morbidelli.morbidelli_design.model.MotorcycleModel

class MotorcycleModelsActivity : AppCompatActivity() {

    private lateinit var rvMotorcycleModels: RecyclerView
    private lateinit var motorcycleAdapter: MotorcycleModelAdapter
    private lateinit var tvTrail: TextView
    private lateinit var tvCruisers: TextView
    private lateinit var tvNaked: TextView
    private lateinit var tvNakedZ: TextView
    private lateinit var tvNakedRetro: TextView

    private var selectedCategory = "Trail"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motorcycle_models)

        setupViews()
        setupRecyclerView()
        setupCategoryTabs()
        setupClickListeners()
        loadMotorcycleModels()
    }

    private fun setupViews() {
        rvMotorcycleModels = findViewById(R.id.rv_motorcycle_models)
        tvTrail = findViewById(R.id.tv_trail)
        tvCruisers = findViewById(R.id.tv_cruisers)
        tvNaked = findViewById(R.id.tv_naked)
        tvNakedZ = findViewById(R.id.tv_naked_z)
        tvNakedRetro = findViewById(R.id.tv_naked_retro)
    }

    private fun setupRecyclerView() {
        motorcycleAdapter = MotorcycleModelAdapter(
            onBookTestRideClick = { model ->
                // Handle book test ride click
                val intent = Intent(this, BookTestRideActivity::class.java)
                intent.putExtra("model_name", model.name)
                startActivity(intent)
            },
            onViewDetailsClick = { model ->
                // Handle view details click
                val intent = Intent(this, ModelDetailsActivity::class.java)
                intent.putExtra("model_name", model.name)
                startActivity(intent)
            }
        )

        rvMotorcycleModels.layoutManager = LinearLayoutManager(this)
        rvMotorcycleModels.adapter = motorcycleAdapter
    }

    private fun setupCategoryTabs() {
        updateCategorySelection()
    }

    private fun setupClickListeners() {
        tvTrail.setOnClickListener { selectCategory("Trail") }
        tvCruisers.setOnClickListener { selectCategory("Cruisers") }
        tvNaked.setOnClickListener { selectCategory("Naked") }
        tvNakedZ.setOnClickListener { selectCategory("Naked Z") }
        tvNakedRetro.setOnClickListener { selectCategory("Naked Retro") }
    }

    private fun selectCategory(category: String) {
        selectedCategory = category
        updateCategorySelection()
        loadMotorcycleModels()
    }

    private fun updateCategorySelection() {
        // Reset all tabs
        tvTrail.setTextColor(getColor(R.color.text_secondary))
        tvCruisers.setTextColor(getColor(R.color.text_secondary))
        tvNaked.setTextColor(getColor(R.color.text_secondary))
        tvNakedZ.setTextColor(getColor(R.color.text_secondary))
        tvNakedRetro.setTextColor(getColor(R.color.text_secondary))

        // Highlight selected tab
        when (selectedCategory) {
            "Trail" -> tvTrail.setTextColor(getColor(R.color.primary_cyan))
            "Cruisers" -> tvCruisers.setTextColor(getColor(R.color.primary_cyan))
            "Naked" -> tvNaked.setTextColor(getColor(R.color.primary_cyan))
            "Naked Z" -> tvNakedZ.setTextColor(getColor(R.color.primary_cyan))
            "Naked Retro" -> tvNakedRetro.setTextColor(getColor(R.color.primary_cyan))
        }
    }

    private fun loadMotorcycleModels() {
        val models = when (selectedCategory) {
            "Trail" -> getTrailModels()
            "Cruisers" -> getCruiserModels()
            "Naked" -> getNakedModels()
            "Naked Z" -> getNakedZModels()
            "Naked Retro" -> getNakedRetroModels()
            else -> getTrailModels()
        }
        motorcycleAdapter.updateModels(models)
    }

    private fun getTrailModels(): List<MotorcycleModel> {
        return listOf(
            MotorcycleModel(
                id = 1,
                name = "T352X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Trail",
                priceFrom = "N/A",
                power = "29.5/10000",
                curbWeight = "151",
                maxTorque = "26.4N·m/6000 r/min"
            ),
            MotorcycleModel(
                id = 2,
                name = "T502X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Trail",
                priceFrom = "N/A",
                power = "29.5/10000",
                curbWeight = "151",
                maxTorque = "26.4N·m/6000 r/min"
            ),
            MotorcycleModel(
                id = 3,
                name = "T702VX",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Trail",
                priceFrom = "N/A",
                power = "29.5/10000",
                curbWeight = "151",
                maxTorque = "26.4N·m/6000 r/min"
            )
        )
    }

    private fun getCruiserModels(): List<MotorcycleModel> {
        return listOf(
            MotorcycleModel(
                id = 4,
                name = "C100X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Cruisers",
                priceFrom = "N/A",
                power = "45.2/8500",
                curbWeight = "185",
                maxTorque = "38.5N·m/6500 r/min"
            )
        )
    }

    private fun getNakedModels(): List<MotorcycleModel> {
        return listOf(
            MotorcycleModel(
                id = 5,
                name = "N200X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Naked",
                priceFrom = "N/A",
                power = "35.8/9500",
                curbWeight = "165",
                maxTorque = "32.1N·m/7500 r/min"
            )
        )
    }

    private fun getNakedZModels(): List<MotorcycleModel> {
        return listOf(
            MotorcycleModel(
                id = 6,
                name = "NZ300X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Naked Z",
                priceFrom = "N/A",
                power = "42.3/9000",
                curbWeight = "172",
                maxTorque = "35.8N·m/7000 r/min"
            )
        )
    }

    private fun getNakedRetroModels(): List<MotorcycleModel> {
        return listOf(
            MotorcycleModel(
                id = 7,
                name = "NR400X",
                imageResource = R.drawable.ic_launcher_foreground,
                category = "Naked Retro",
                priceFrom = "N/A",
                power = "38.7/8800",
                curbWeight = "158",
                maxTorque = "33.2N·m/7200 r/min"
            )
        )
    }
}
