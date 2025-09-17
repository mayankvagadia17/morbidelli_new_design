package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.RideModel

class RideAdapter(
    private val rideList: List<RideModel>,
    private val onItemClick: (RideModel) -> Unit
) : RecyclerView.Adapter<RideAdapter.RideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ride, parent, false)
        return RideViewHolder(view)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val ride = rideList[position]
        holder.bind(ride)
    }

    override fun getItemCount(): Int = rideList.size

    inner class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivBikeImage: ImageView = itemView.findViewById(R.id.iv_bike_image)
        private val tvBikeName: TextView = itemView.findViewById(R.id.tv_bike_name)
        private val tvPowerSpec: TextView = itemView.findViewById(R.id.tv_power_spec)
        private val tvWeightSpec: TextView = itemView.findViewById(R.id.tv_weight_spec)

        fun bind(ride: RideModel) {
            ivBikeImage.setImageResource(ride.bikeImage)
            tvBikeName.text = ride.bikeName
            tvPowerSpec.text = ride.powerSpec
            tvWeightSpec.text = ride.weightSpec

            itemView.setOnClickListener {
                onItemClick(ride)
            }
        }
    }
}
