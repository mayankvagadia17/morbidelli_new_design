package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.MotorcycleModel

class MotorcycleModelAdapter(
    private val onBookTestRideClick: (MotorcycleModel) -> Unit,
    private val onViewDetailsClick: (MotorcycleModel) -> Unit
) : RecyclerView.Adapter<MotorcycleModelAdapter.MotorcycleViewHolder>() {

    private var motorcycleModels = listOf<MotorcycleModel>()

    fun updateModels(models: List<MotorcycleModel>) {
        motorcycleModels = models
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotorcycleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_motorcycle_model, parent, false)
        return MotorcycleViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotorcycleViewHolder, position: Int) {
        holder.bind(motorcycleModels[position])
    }

    override fun getItemCount(): Int = motorcycleModels.size

    inner class MotorcycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMotorcycleImage: ImageView = itemView.findViewById(R.id.iv_motorcycle_image)
        private val tvModelName: TextView = itemView.findViewById(R.id.tv_model_name)
        private val tvPriceFrom: TextView = itemView.findViewById(R.id.tv_price_from)
        private val tvPower: TextView = itemView.findViewById(R.id.tv_power)
        private val tvCurbWeight: TextView = itemView.findViewById(R.id.tv_curb_weight)
        private val tvMaxTorque: TextView = itemView.findViewById(R.id.tv_max_torque)
        private val btnBookTestRide: Button = itemView.findViewById(R.id.btn_book_test_ride)
        private val btnViewDetails: Button = itemView.findViewById(R.id.btn_view_details)

        fun bind(model: MotorcycleModel) {
            ivMotorcycleImage.setImageResource(model.imageResource)
            tvModelName.text = model.name
            tvPriceFrom.text = model.priceFrom
            tvPower.text = model.power
            tvCurbWeight.text = model.curbWeight
            tvMaxTorque.text = model.maxTorque

            btnBookTestRide.setOnClickListener {
                onBookTestRideClick(model)
            }

            btnViewDetails.setOnClickListener {
                onViewDetailsClick(model)
            }
        }
    }
}
