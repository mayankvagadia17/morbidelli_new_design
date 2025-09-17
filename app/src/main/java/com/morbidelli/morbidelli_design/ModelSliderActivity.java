package com.morbidelli.morbidelli_design;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.morbidelli.morbidelli_design.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class ModelSliderActivity extends AppCompatActivity {
    
    // Intent extras constants
    public static final String EXTRA_CATEGORY = "extra_category";
    public static final String EXTRA_INITIAL_POSITION = "extra_initial_position";
    
    private ViewPager2 viewPager;
    private ModelSliderAdapter adapter;
    private LinearLayout dotsIndicator;
    private TextView pageCounter;
    private ImageView btnClose;
    private List<Vehicle> vehicles;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_slider);
        
        initViews();
        setupVehicleData();
        setupAdapter();
        setupPageIndicators();
        setupListeners();
        
        // Handle intent extras
        handleIntent();
    }
    
    private void initViews() {
        viewPager = findViewById(R.id.view_pager);
        dotsIndicator = findViewById(R.id.dots_indicator);
        pageCounter = findViewById(R.id.page_counter);
        btnClose = findViewById(R.id.btn_close);
    }
    
    private void setupVehicleData() {
        vehicles = new ArrayList<>();
        
        // Add sample vehicle data
        vehicles.add(new Vehicle(1, "N300", "The most advanced electric vehicle with cutting-edge technology.", 
                R.drawable.vehicle_n300, "Electric", "300", "85 kWh", "450 km", "0-100 km/h in 4.2s"));
        
        vehicles.add(new Vehicle(2, "T400", "Powerful and efficient hybrid engine with premium comfort.", 
                R.drawable.vehicle_t400, "Hybrid", "400", "65 kWh", "650 km", "0-100 km/h in 5.8s"));
        
        vehicles.add(new Vehicle(3, "S500", "Sport performance meets luxury with advanced aerodynamics.", 
                R.drawable.vehicle_s500, "Gasoline", "500", "70L", "800 km", "0-100 km/h in 3.6s"));
        
        vehicles.add(new Vehicle(4, "X600", "Ultimate luxury SUV with all-terrain capabilities.", 
                R.drawable.vehicle_x600, "Hybrid", "600", "80 kWh", "700 km", "0-100 km/h in 4.9s"));
        
        vehicles.add(new Vehicle(5, "E700", "Revolutionary electric sports car with unprecedented range.", 
                R.drawable.vehicle_e700, "Electric", "700", "100 kWh", "550 km", "0-100 km/h in 2.8s"));
    }
    
    private void setupAdapter() {
        adapter = new ModelSliderAdapter(vehicles, this::onVehicleSelect);
        viewPager.setAdapter(adapter);
        
        // Set initial page counter
        updatePageCounter(0);
    }
    
    private void setupPageIndicators() {
        createDots();
        updateDots(0);
        
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
                updatePageCounter(position);
            }
        });
    }
    
    private void createDots() {
        dotsIndicator.removeAllViews();
        
        for (int i = 0; i < vehicles.size(); i++) {
            ImageButton dot = new ImageButton(this);
            dot.setBackground(null);
            dot.setPadding(8, 8, 8, 8);
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(params);
            
            final int position = i;
            dot.setOnClickListener(v -> viewPager.setCurrentItem(position));
            
            dotsIndicator.addView(dot);
        }
    }
    
    private void updateDots(int currentPosition) {
        for (int i = 0; i < dotsIndicator.getChildCount(); i++) {
            ImageButton dot = (ImageButton) dotsIndicator.getChildAt(i);
            if (i == currentPosition) {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_unselected));
            }
        }
    }
    
    private void updatePageCounter(int position) {
        String counterText = (position + 1) + " / " + vehicles.size();
        pageCounter.setText(counterText);
    }
    
    private void setupListeners() {
        btnClose.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("vehicle_id")) {
            int vehicleId = intent.getIntExtra("vehicle_id", -1);
            
            // Find the position of the vehicle with the given ID
            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).getId() == vehicleId) {
                    viewPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
        
        if (intent.hasExtra("position")) {
            int position = intent.getIntExtra("position", 0);
            if (position >= 0 && position < vehicles.size()) {
                viewPager.setCurrentItem(position, false);
            }
        }
    }
    
    private void onVehicleSelect(Vehicle vehicle) {
        Intent intent = new Intent(this, ModelDetailsActivity.class);
        intent.putExtra("vehicle_id", vehicle.getId());
        intent.putExtra("vehicle_name", vehicle.getName());
        intent.putExtra("vehicle_description", vehicle.getDescription());
        intent.putExtra("vehicle_image", vehicle.getImageResource());
        intent.putExtra("vehicle_type", vehicle.getType());
        intent.putExtra("vehicle_power", vehicle.getPower());
        intent.putExtra("vehicle_battery", vehicle.getBattery());
        intent.putExtra("vehicle_range", vehicle.getRange());
        intent.putExtra("vehicle_acceleration", vehicle.getAcceleration());
        startActivity(intent);
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
