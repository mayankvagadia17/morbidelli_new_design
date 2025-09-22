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

        fun bind(location: LocationModel, position: Int) {
            tvDealershipName.text = location.dealershipName
            tvAddress.text = location.address
            tvAvailableMotorbikes.text = "Available motorbike: 1"
            tvFromDate.text = location.fromDate

            // Set radio button state
            rbSelectLocation.isChecked = selectedPosition == position

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
                onViewMoreClick(location)
            }

            ivDropdownArrow.setOnClickListener {
                onViewMoreClick(location)
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
}
