package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.LocationModel

class LocationListAdapter(
    private var locations: MutableList<LocationModel>,
    private val onLocationClick: (LocationModel) -> Unit,
    private val onViewMoreClick: (LocationModel) -> Unit,
    private val onExternalLinkClick: (LocationModel) -> Unit
) : RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>() {

    private var selectedPosition = -1

    inner class LocationListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDealershipName: TextView = itemView.findViewById(R.id.tv_dealership_name)
        private val tvAddress: TextView = itemView.findViewById(R.id.tv_address)
        private val tvAvailableMotorbikes: TextView = itemView.findViewById(R.id.tv_available_motorbikes)
        private val tvFromDate: TextView = itemView.findViewById(R.id.tv_from_date)
        private val tvShowMore: TextView = itemView.findViewById(R.id.tv_show_more)
        private val rbSelectLocation: RadioButton = itemView.findViewById(R.id.rb_select_location)
        private val ivExternalLink: ImageView = itemView.findViewById(R.id.iv_external_link)
        private val ivDropdownArrow: ImageView = itemView.findViewById(R.id.iv_dropdown_arrow)
        private val divider: View = itemView.findViewById(R.id.divider)
        
        // Expanded view elements
        private val expandedDetails: View = itemView.findViewById(R.id.expanded_details)
        private val tvWeekdaysHours: TextView = itemView.findViewById(R.id.tv_weekdays_hours)
        private val tvWeekendHours: TextView = itemView.findViewById(R.id.tv_weekend_hours)
        private val tvPhoneNumber: TextView = itemView.findViewById(R.id.tv_phone_number)
        private val tvEmail: TextView = itemView.findViewById(R.id.tv_email)
        
        // Container for selection border
        private val locationItemContainer: View = itemView.findViewById(R.id.location_item_container)

        fun bind(location: LocationModel, position: Int) {
            tvDealershipName.text = location.dealershipName
            tvAddress.text = location.address
            tvAvailableMotorbikes.text = "Available motorbike: ${location.availableMotorbikes}"
            tvFromDate.text = location.fromDate

            // Set radio button state and selection border
            val isSelected = selectedPosition == position
            rbSelectLocation.isChecked = isSelected
            
            // Apply selection border
            if (isSelected) {
                locationItemContainer.setBackgroundResource(R.drawable.selected_location_border)
            } else {
                locationItemContainer.setBackgroundResource(R.drawable.unselected_location_border)
            }

            // Handle expanded view
            if (location.isExpanded) {
                expandedDetails.visibility = View.VISIBLE
                tvShowMore.text = "Show less"
                ivDropdownArrow.rotation = 180f
                
                // Populate expanded details
                location.workingHours?.let { hours ->
                    tvWeekdaysHours.text = hours.weekdays
                    tvWeekendHours.text = hours.weekend
                }
                
                location.contactInfo?.let { contact ->
                    tvPhoneNumber.text = contact.phoneNumber
                    tvEmail.text = contact.email
                }
            } else {
                expandedDetails.visibility = View.GONE
                tvShowMore.text = "Show more"
                ivDropdownArrow.rotation = 0f
            }

            // Hide divider for last item
            if (position == locations.size - 1) {
                divider.visibility = View.GONE
            } else {
                divider.visibility = View.VISIBLE
            }

            // Set click listeners
            itemView.setOnClickListener {
                if (selectedPosition != position) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(position)
                    onLocationClick(location)
                }
            }

            rbSelectLocation.setOnClickListener {
                if (selectedPosition != position) {
                    val previousPosition = selectedPosition
                    selectedPosition = position
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(position)
                    onLocationClick(location)
                }
            }

            tvShowMore.setOnClickListener {
                toggleExpandedState(position)
            }

            ivDropdownArrow.setOnClickListener {
                toggleExpandedState(position)
            }

            ivExternalLink.setOnClickListener {
                onExternalLinkClick(location)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location_list, parent, false)
        return LocationListViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.bind(locations[position], position)
    }

    override fun getItemCount(): Int = locations.size

    fun updateLocations(newLocations: List<LocationModel>) {
        locations.clear()
        locations.addAll(newLocations)
        selectedPosition = -1 // Reset selection
        notifyDataSetChanged()
    }

    fun setSelectedLocation(locationId: Int) {
        val position = locations.indexOfFirst { it.id == locationId }
        if (position != -1 && position != selectedPosition) {
            val previousPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousPosition)
            notifyItemChanged(position)
        }
    }

    private fun toggleExpandedState(position: Int) {
        if (position >= 0 && position < locations.size) {
            val location = locations[position]
            val updatedLocation = location.copy(isExpanded = !location.isExpanded)
            locations[position] = updatedLocation
            notifyItemChanged(position)
        }
    }
}
