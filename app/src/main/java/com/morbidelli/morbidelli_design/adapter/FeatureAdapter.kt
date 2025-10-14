package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.Feature

class FeatureAdapter : RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder>() {

    private var features = listOf<Feature>()

    fun updateData(newFeatures: List<Feature>) {
        features = newFeatures
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feature, parent, false)
        return FeatureViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(features[position])
    }

    override fun getItemCount(): Int = features.size

    class FeatureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivIcon: ImageView = itemView.findViewById(R.id.iv_feature_icon)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_feature_title)
        private val tvDescription: TextView = itemView.findViewById(R.id.tv_feature_description)

        fun bind(feature: Feature) {
            ivIcon.setImageResource(feature.iconResId)
            tvTitle.text = feature.title
            tvDescription.text = feature.description
        }
    }
}
