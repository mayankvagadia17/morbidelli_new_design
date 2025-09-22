package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.LocationModel

class LocationAdapter(
    private var locations: MutableList<LocationModel>,
    private val onLocationClick: (LocationModel) -> Unit,
    private val onViewMoreClick: (LocationModel) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDealershipName: TextView = itemView.findViewById(R.id.tv_dealership_name)
        private val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        private val tvFromDate: TextView = itemView.findViewById(R.id.tv_from_date)
        private val tvViewMore: TextView = itemView.findViewById(R.id.tv_view_more)
        private val ivArrow: ImageView = itemView.findViewById(R.id.iv_arrow)

        fun bind(location: LocationModel) {
            tvDealershipName.text = location.dealershipName
            tvAddress.text = location.address
            tvFromDate.text = location.fromDate

            itemView.setOnClickListener {
                onLocationClick(location)
            }

            tvViewMore.setOnClickListener {
                onViewMoreClick(location)
            }

            // Update selection state if needed
            if (location.isSelected) {
                itemView.setBackgroundResource(R.drawable.vehicle_selected_border)
            } else {
                itemView.setBackgroundResource(R.drawable.vehicle_unselected_border)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location_selection, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int = locations.size
    
    fun updateLocations(newLocations: List<LocationModel>) {
        locations.clear()
        locations.addAll(newLocations)
        notifyDataSetChanged()
    }
}
