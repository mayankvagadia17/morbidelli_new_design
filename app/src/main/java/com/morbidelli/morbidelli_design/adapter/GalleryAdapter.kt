package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.GalleryItem
import com.morbidelli.morbidelli_design.model.GalleryItemType

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var galleryItems = listOf<GalleryItem>()

    fun updateData(newItems: List<GalleryItem>) {
        galleryItems = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(galleryItems[position])
    }

    override fun getItemCount(): Int = galleryItems.size

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivImage: ImageView = itemView.findViewById(R.id.iv_image)
        private val tvDuration: TextView = itemView.findViewById(R.id.tv_duration)
        private val ivPlayButton: ImageView = itemView.findViewById(R.id.iv_play_button)
        private val ivVolume: ImageView = itemView.findViewById(R.id.iv_volume)

        fun bind(item: GalleryItem) {
            ivImage.setImageResource(item.resourceId)
            
            if (item.type == GalleryItemType.VIDEO) {
                tvDuration.visibility = View.VISIBLE
                ivPlayButton.visibility = View.VISIBLE
                ivVolume.visibility = View.VISIBLE
                tvDuration.text = item.videoDuration
            } else {
                tvDuration.visibility = View.GONE
                ivPlayButton.visibility = View.GONE
                ivVolume.visibility = View.GONE
            }
        }
    }
}
