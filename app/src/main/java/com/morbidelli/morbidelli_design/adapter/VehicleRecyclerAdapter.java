package com.morbidelli.morbidelli_design.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.morbidelli.morbidelli_design.R;
import com.morbidelli.morbidelli_design.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter for the vehicle grid with selection functionality
 */
public class VehicleRecyclerAdapter extends RecyclerView.Adapter<VehicleRecyclerAdapter.VehicleViewHolder> {
    
    private Context context;
    private List<Vehicle> vehicles;
    private OnVehicleClickListener clickListener;
    private int selectedPosition = -1; // Track selected item position
    
    public interface OnVehicleClickListener {
        void onVehicleClick(Vehicle vehicle, int position);
        void onViewDetailsClick(Vehicle vehicle, int position);
    }
    
    public VehicleRecyclerAdapter(Context context, List<Vehicle> vehicles) {
        this.context = context;
        this.vehicles = vehicles;
    }
    
    public void setOnVehicleClickListener(OnVehicleClickListener listener) {
        this.clickListener = listener;
    }
    
    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        
        // Notify changes for both old and new selected items
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition);
        }
        if (selectedPosition != -1) {
            notifyItemChanged(selectedPosition);
        }
    }
    
    public int getSelectedPosition() {
        return selectedPosition;
    }
    
    public Vehicle getSelectedVehicle() {
        if (selectedPosition >= 0 && selectedPosition < vehicles.size()) {
            return vehicles.get(selectedPosition);
        }
        return null;
    }
    
    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {
        Vehicle vehicle = vehicles.get(position);
        boolean isSelected = (position == selectedPosition);
        holder.bind(vehicle, position, isSelected);
    }
    
    @Override
    public int getItemCount() {
        return vehicles.size();
    }
    
    /**
     * Update the vehicle list and notify adapter
     */
    public void updateVehicles(List<Vehicle> newVehicles) {
        if (newVehicles == null) {
            // Handle null case
            this.vehicles = new ArrayList<>();
        } else {
            // Create a new list to avoid reference issues
            this.vehicles = new ArrayList<>(newVehicles);
        }
        // Reset selection when vehicles change
        selectedPosition = -1;
        notifyDataSetChanged();
    }
    
    class VehicleViewHolder extends RecyclerView.ViewHolder {
        private ImageView vehicleImage;
        private TextView vehicleName;
        private LinearLayout viewDetailsButton;
        
        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleImage = itemView.findViewById(R.id.iv_vehicle_image);
            vehicleName = itemView.findViewById(R.id.tv_vehicle_name);
            viewDetailsButton = itemView.findViewById(R.id.btn_view_details);
        }
        
        public void bind(Vehicle vehicle, int position, boolean isSelected) {
            vehicleName.setText(vehicle.getName());
            vehicleImage.setImageResource(vehicle.getImageResId());
            
            // Update background based on selection state
            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.vehicle_item_selected);
            } else {
                itemView.setBackgroundResource(R.drawable.vehicle_item_unselected);
            }
            
            // Set click listeners
            if (clickListener != null) {
                itemView.setOnClickListener(v -> {
                    // Update selection and notify adapter
                    setSelectedPosition(position);
                    clickListener.onVehicleClick(vehicle, position);
                });
                viewDetailsButton.setOnClickListener(v -> clickListener.onViewDetailsClick(vehicle, position));
            }
        }
    }
}
