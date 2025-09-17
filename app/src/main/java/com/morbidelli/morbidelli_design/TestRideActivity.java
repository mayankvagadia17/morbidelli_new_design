package com.morbidelli.morbidelli_design;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.morbidelli.morbidelli_design.adapter.VehicleRecyclerAdapter;
import com.morbidelli.morbidelli_design.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRideActivity extends Activity implements VehicleRecyclerAdapter.OnVehicleClickListener {
    
    private RecyclerView vehiclesGrid;
    private VehicleRecyclerAdapter vehicleAdapter;
    private List<Vehicle> allVehicles;
    private Map<String, List<Vehicle>> vehiclesByCategory;
    private String selectedCategory = "Naked";
    private Vehicle selectedVehicle;
    
    // UI Elements
    private TextView tabTrail, tabNaked, tabStreetFighter, tabScooters, tabCruiser;
    private TextView btnContactUs;
    private Button btnContinue;
    private ImageView btnClose;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ride);
        
        initViews();
        initData();
        setupClickListeners();
        setupGrid();
        
        // Update tab selection for default category
        updateTabSelection();
    }
    
    private void initViews() {
        vehiclesGrid = findViewById(R.id.vehicles_grid);
        
        // Category tabs
        tabTrail = findViewById(R.id.tab_trail);
        tabNaked = findViewById(R.id.tab_naked);
        tabStreetFighter = findViewById(R.id.tab_street_fighter);
        tabScooters = findViewById(R.id.tab_scooters);
        tabCruiser = findViewById(R.id.tab_cruiser);
        
        // Buttons
        btnContactUs = findViewById(R.id.btn_contact_us);
        btnContinue = findViewById(R.id.btn_continue);
        btnClose = findViewById(R.id.btn_close);
    }
    
    private void initData() {
        allVehicles = new ArrayList<>();
        vehiclesByCategory = new HashMap<>();
        
        // Initialize sample data
        // Naked category vehicles
        List<Vehicle> nakedVehicles = new ArrayList<>();
        nakedVehicles.add(new Vehicle("N300", R.drawable.ic_bike_n300, "Naked"));
        nakedVehicles.add(new Vehicle("N250R", R.drawable.ic_bike_n250r, "Naked"));
        nakedVehicles.add(new Vehicle("N300R", R.drawable.ic_bike_n300, "Naked"));
        nakedVehicles.add(new Vehicle("M502N", R.drawable.ic_bike_n300, "Naked"));
        
        // Trail category vehicles
        List<Vehicle> trailVehicles = new ArrayList<>();
        trailVehicles.add(new Vehicle("T700", R.drawable.ic_bike_n300, "Trail"));
        trailVehicles.add(new Vehicle("T900", R.drawable.ic_bike_n250r, "Trail"));
        
        // Street Fighter category vehicles
        List<Vehicle> streetFighterVehicles = new ArrayList<>();
        streetFighterVehicles.add(new Vehicle("SF650", R.drawable.ic_bike_n300, "Street Fighter"));
        streetFighterVehicles.add(new Vehicle("SF850", R.drawable.ic_bike_n250r, "Street Fighter"));
        
        // Scooters category vehicles
        List<Vehicle> scooterVehicles = new ArrayList<>();
        scooterVehicles.add(new Vehicle("S150", R.drawable.ic_bike_n300, "Scooters"));
        scooterVehicles.add(new Vehicle("S250", R.drawable.ic_bike_n250r, "Scooters"));
        
        // Cruiser category vehicles
        List<Vehicle> cruiserVehicles = new ArrayList<>();
        cruiserVehicles.add(new Vehicle("C500", R.drawable.ic_bike_n300, "Cruiser"));
        cruiserVehicles.add(new Vehicle("C750", R.drawable.ic_bike_n250r, "Cruiser"));
        
        // Add to maps
        vehiclesByCategory.put("Naked", nakedVehicles);
        vehiclesByCategory.put("Trail", trailVehicles);
        vehiclesByCategory.put("Street Fighter", streetFighterVehicles);
        vehiclesByCategory.put("Scooters", scooterVehicles);
        vehiclesByCategory.put("Cruiser", cruiserVehicles);
        
        // Add all vehicles to the main list
        allVehicles.addAll(nakedVehicles);
        allVehicles.addAll(trailVehicles);
        allVehicles.addAll(streetFighterVehicles);
        allVehicles.addAll(scooterVehicles);
        allVehicles.addAll(cruiserVehicles);
    }
    
    private void setupClickListeners() {
        // Category tab listeners
        tabTrail.setOnClickListener(v -> selectCategory("Trail"));
        tabNaked.setOnClickListener(v -> selectCategory("Naked"));
        tabStreetFighter.setOnClickListener(v -> selectCategory("Street Fighter"));
        tabScooters.setOnClickListener(v -> selectCategory("Scooters"));
        tabCruiser.setOnClickListener(v -> selectCategory("Cruiser"));
        
        // Other button listeners
        btnContactUs.setOnClickListener(v -> {
            // Handle contact us action
        });
        
        btnContinue.setOnClickListener(v -> {
            if (selectedVehicle != null) {
                // Handle continue with selected vehicle
                // You can add navigation to next step or show confirmation
            }
        });
        
        // Initially disable continue button until a vehicle is selected
        updateContinueButton();
        
        btnClose.setOnClickListener(v -> {
            finish(); // Close the activity
        });
    }
    
    private void setupGrid() {
        List<Vehicle> initialVehicles = vehiclesByCategory.get(selectedCategory);
        if (initialVehicles == null) {
            initialVehicles = new ArrayList<>();
        }
        
        // Set up GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        vehiclesGrid.setLayoutManager(gridLayoutManager);
        
        // Create and set adapter
        vehicleAdapter = new VehicleRecyclerAdapter(this, initialVehicles);
        vehicleAdapter.setOnVehicleClickListener(this);
        vehiclesGrid.setAdapter(vehicleAdapter);
    }
    
    private void selectCategory(String category) {
        selectedCategory = category;
        showVehiclesForCategory(category);
        updateTabSelection();
    }
    
    private void showVehiclesForCategory(String category) {
        List<Vehicle> categoryVehicles = vehiclesByCategory.get(category);
        if (categoryVehicles == null) {
            categoryVehicles = new ArrayList<>();
        } else {
            // Create a defensive copy to avoid reference issues
            categoryVehicles = new ArrayList<>(categoryVehicles);
        }
        
        // Clear selection when changing categories
        selectedVehicle = null;
        updateContinueButton();
        
        vehicleAdapter.updateVehicles(categoryVehicles);
    }
    
    private void updateTabSelection() {
        // Reset all tabs to unselected state
        resetTabSelection(tabTrail);
        resetTabSelection(tabNaked);
        resetTabSelection(tabStreetFighter);
        resetTabSelection(tabScooters);
        resetTabSelection(tabCruiser);
        
        // Set selected tab
        TextView selectedTab = null;
        switch (selectedCategory) {
            case "Trail":
                selectedTab = tabTrail;
                break;
            case "Naked":
                selectedTab = tabNaked;
                break;
            case "Street Fighter":
                selectedTab = tabStreetFighter;
                break;
            case "Scooters":
                selectedTab = tabScooters;
                break;
            case "Cruiser":
                selectedTab = tabCruiser;
                break;
        }
        
        if (selectedTab != null) {
            setTabSelected(selectedTab);
        }
    }
    
    private void resetTabSelection(TextView tab) {
        tab.setTextColor(getResources().getColor(R.color.category_unselected));
        tab.setBackgroundResource(R.drawable.category_tab_selector);
    }
    
    private void setTabSelected(TextView tab) {
        tab.setTextColor(getResources().getColor(R.color.white));
        tab.setBackgroundResource(R.drawable.category_tab_selected);
    }
    
    private void updateContinueButton() {
        if (selectedVehicle != null) {
            btnContinue.setEnabled(true);
            btnContinue.setAlpha(1.0f);
        } else {
            btnContinue.setEnabled(false);
            btnContinue.setAlpha(0.5f);
        }
    }
    
    // VehicleAdapter.OnVehicleClickListener implementation
    @Override
    public void onVehicleClick(Vehicle vehicle, int position) {
        // Handle vehicle selection
        selectedVehicle = vehicle;
        updateContinueButton();

    }
    
    @Override
    public void onViewDetailsClick(Vehicle vehicle, int position) {
        // Navigate to Model Details Activity
        Intent intent = new Intent(this, ModelDetailsActivity.class);
        intent.putExtra(ModelDetailsActivity.EXTRA_VEHICLE_NAME, vehicle.getName());
        intent.putExtra(ModelDetailsActivity.EXTRA_VEHICLE_IMAGE, vehicle.getImageResId());
        intent.putExtra(ModelDetailsActivity.EXTRA_VEHICLE_CATEGORY, vehicle.getCategory());
        startActivity(intent);
    }
}
