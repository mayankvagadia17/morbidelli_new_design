package com.morbidelli.morbidelli_design;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.morbidelli.morbidelli_design.model.Vehicle;

public class ModelDetailsActivity extends AppCompatActivity {
    
    private ImageView btnClose;
    private ImageView vehicleImage;
    private TextView vehicleName;
    private TextView vehicleSubtitle;
    private TextView vehicleDescription;
    private TextView priceValue;
    private TextView powerValue;
    private TextView curbWeightValue;
    private TextView maxTorqueValue;
    private Button selectModelButton;
    
    private Vehicle vehicle;
    
    public static final String EXTRA_VEHICLE_NAME = "vehicle_name";
    public static final String EXTRA_VEHICLE_IMAGE = "vehicle_image";
    public static final String EXTRA_VEHICLE_CATEGORY = "vehicle_category";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_details);
        
        setupToolbar();
        initViews();
        loadVehicleData();
        setupClickListeners();
    }
    
    private void setupToolbar() {
        // Set up the toolbar if present in the layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            
            // Enable back navigation
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Model Details");
            }
        } else {
            // If no toolbar found, show fallback header and set up close button
            android.view.View fallbackHeader = findViewById(R.id.fallback_header);
            if (fallbackHeader != null) {
                fallbackHeader.setVisibility(android.view.View.VISIBLE);
            }
        }
    }
    
    private void initViews() {
        btnClose = findViewById(R.id.btn_close);
        vehicleImage = findViewById(R.id.iv_vehicle_image);
        vehicleName = findViewById(R.id.tv_vehicle_name);
        vehicleSubtitle = findViewById(R.id.tv_vehicle_subtitle);
        vehicleDescription = findViewById(R.id.tv_vehicle_description);
        priceValue = findViewById(R.id.tv_price_value);
        powerValue = findViewById(R.id.tv_power_value);
        curbWeightValue = findViewById(R.id.tv_curb_weight_value);
        maxTorqueValue = findViewById(R.id.tv_max_torque_value);
        selectModelButton = findViewById(R.id.btn_select_model);
    }
    
    private void loadVehicleData() {
        Intent intent = getIntent();
        String vehicleNameStr = intent.getStringExtra(EXTRA_VEHICLE_NAME);
        int vehicleImageRes = intent.getIntExtra(EXTRA_VEHICLE_IMAGE, R.drawable.placeholder_motorcycle);
        String vehicleCategory = intent.getStringExtra(EXTRA_VEHICLE_CATEGORY);
        
        // Create vehicle object for easier handling
        vehicle = new Vehicle(vehicleNameStr, vehicleImageRes, vehicleCategory);
        
        // Set the data
        vehicleName.setText(vehicleNameStr);
        vehicleImage.setImageResource(vehicleImageRes);
        
        // Set specific data based on vehicle name
        setVehicleSpecificData(vehicleNameStr);
    }
    
    private void setVehicleSpecificData(String name) {
        switch (name) {
            case "N300":
                vehicleSubtitle.setText("Morbidelli N300: Ride Art, Master Roads");
                vehicleDescription.setText("The Morbidelli N300 is more than a motorcycle; it's a statement of style and performance.");
                priceValue.setText("N/A");
                powerValue.setText("29.5/10000");
                curbWeightValue.setText("151");
                maxTorqueValue.setText("26.4N·m/6000 r/min");
                break;
            case "N250R":
                vehicleSubtitle.setText("Morbidelli N250R: Compact Power, Big Dreams");
                vehicleDescription.setText("The Morbidelli N250R delivers exceptional performance in a compact, stylish package.");
                priceValue.setText("N/A");
                powerValue.setText("25.2/9500");
                curbWeightValue.setText("145");
                maxTorqueValue.setText("22.8N·m/5800 r/min");
                break;
            case "N300R":
                vehicleSubtitle.setText("Morbidelli N300R: Racing Spirit, Street Ready");
                vehicleDescription.setText("The Morbidelli N300R combines racing heritage with everyday usability.");
                priceValue.setText("N/A");
                powerValue.setText("31.2/10500");
                curbWeightValue.setText("149");
                maxTorqueValue.setText("28.1N·m/6200 r/min");
                break;
            case "M502N":
                vehicleSubtitle.setText("Morbidelli M502N: Power Redefined");
                vehicleDescription.setText("The Morbidelli M502N represents the pinnacle of naked bike engineering.");
                priceValue.setText("N/A");
                powerValue.setText("47.6/8500");
                curbWeightValue.setText("189");
                maxTorqueValue.setText("45.2N·m/6800 r/min");
                break;
            default:
                vehicleSubtitle.setText("Morbidelli: Ride Art, Master Roads");
                vehicleDescription.setText("Experience the perfect blend of Italian design and engineering excellence.");
                priceValue.setText("N/A");
                powerValue.setText("--/--");
                curbWeightValue.setText("--");
                maxTorqueValue.setText("--N·m/-- r/min");
                break;
        }
    }
    
    private void setupClickListeners() {
        btnClose.setOnClickListener(v -> finish());
        
        selectModelButton.setOnClickListener(v -> {
            // Handle model selection - you can add your logic here
            // For now, just close the activity
            setResult(RESULT_OK);
            finish();
        });
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Handle back navigation
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
