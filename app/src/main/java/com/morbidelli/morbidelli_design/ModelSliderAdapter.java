package com.morbidelli.morbidelli_design;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.morbidelli.morbidelli_design.model.Vehicle;
import java.util.List;

public class ModelSliderAdapter extends RecyclerView.Adapter<ModelSliderAdapter.ModelViewHolder> {

    public interface OnVehicleSelectListener {
        void onVehicleSelect(Vehicle vehicle);
    }

    private List<Vehicle> vehicles;
    private OnVehicleSelectListener listener;

    public ModelSliderAdapter(List<Vehicle> vehicles, OnVehicleSelectListener listener) {
        this.vehicles = vehicles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_model_slider, parent, false);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        holder.bind(vehicle, listener);
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    static class ModelViewHolder extends RecyclerView.ViewHolder {
        
        private ImageView vehicleImage;
        private TextView vehicleName;
        private TextView vehicleSubtitle;
        private TextView vehicleDescription;
        private TextView priceValue;
        private TextView powerValue;
        private TextView maxTorqueValue;
        private Button selectButton;
        
        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            
            vehicleImage = itemView.findViewById(R.id.iv_vehicle_image);
            vehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            vehicleSubtitle = itemView.findViewById(R.id.tv_vehicle_subtitle);
            vehicleDescription = itemView.findViewById(R.id.tv_vehicle_description);
            priceValue = itemView.findViewById(R.id.tv_price_value);
            powerValue = itemView.findViewById(R.id.tv_power_value);
            maxTorqueValue = itemView.findViewById(R.id.tv_max_torque_value);
            selectButton = itemView.findViewById(R.id.btn_select_vehicle);
        }
        
        public void bind(Vehicle vehicle, OnVehicleSelectListener listener) {
            vehicleImage.setImageResource(vehicle.getImageResource());
            vehicleName.setText(vehicle.getName());
            
            // Set vehicle-specific data based on vehicle name
            setVehicleSpecificData(vehicle.getName());
            
            selectButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVehicleSelect(vehicle);
                }
            });
            
            // Set content descriptions for accessibility
            vehicleImage.setContentDescription("Image of " + vehicle.getName());
            selectButton.setContentDescription("Select " + vehicle.getName());
        }
        
        private void setVehicleSpecificData(String name) {
            switch (name) {
                case "N300":
                    vehicleSubtitle.setText("Morbidelli N300: Ride Art, Master Roads");
                    vehicleDescription.setText("The Morbidelli N300 is more than a motorcycle; it's a statement of style and performance.");
                    priceValue.setText("N/A");
                    powerValue.setText("29.5/10000");
                    maxTorqueValue.setText("26.4N·m/6000 r/min");
                    break;
                case "T400":
                    vehicleSubtitle.setText("Morbidelli T400: Trail Master");
                    vehicleDescription.setText("The Morbidelli T400 delivers exceptional performance on any terrain.");
                    priceValue.setText("N/A");
                    powerValue.setText("35.2/9500");
                    maxTorqueValue.setText("32.1N·m/6200 r/min");
                    break;
                case "S500":
                    vehicleSubtitle.setText("Morbidelli S500: Sport Performance");
                    vehicleDescription.setText("The Morbidelli S500 combines racing heritage with everyday usability.");
                    priceValue.setText("N/A");
                    powerValue.setText("42.6/10500");
                    maxTorqueValue.setText("38.1N·m/7200 r/min");
                    break;
                case "X600":
                    vehicleSubtitle.setText("Morbidelli X600: Ultimate Power");
                    vehicleDescription.setText("The Morbidelli X600 represents the pinnacle of motorcycle engineering.");
                    priceValue.setText("N/A");
                    powerValue.setText("55.8/11000");
                    maxTorqueValue.setText("52.2N·m/8800 r/min");
                    break;
                case "E700":
                    vehicleSubtitle.setText("Morbidelli E700: Electric Future");
                    vehicleDescription.setText("The Morbidelli E700 represents the future of electric motorcycles.");
                    priceValue.setText("N/A");
                    powerValue.setText("65.5/12000");
                    maxTorqueValue.setText("68.4N·m/9500 r/min");
                    break;
                default:
                    vehicleSubtitle.setText("Morbidelli: Ride Art, Master Roads");
                    vehicleDescription.setText("Experience the perfect blend of Italian design and engineering excellence.");
                    priceValue.setText("N/A");
                    powerValue.setText("--/--");
                    maxTorqueValue.setText("--N·m/-- r/min");
                    break;
            }
        }
    }
}
