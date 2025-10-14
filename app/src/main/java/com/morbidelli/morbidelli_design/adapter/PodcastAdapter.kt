package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.PodcastItem

class PodcastAdapter : RecyclerView.Adapter<PodcastAdapter.PodcastViewHolder>() {

    private var podcasts = listOf<PodcastItem>()

    fun updateData(newPodcasts: List<PodcastItem>) {
        podcasts = newPodcasts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_podcast, parent, false)
        return PodcastViewHolder(view)
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.bind(podcasts[position])
    }

    override fun getItemCount(): Int = podcasts.size

    inner class PodcastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val tvSource: TextView = itemView.findViewById(R.id.tv_source)
        private val tvTimeAgo: TextView = itemView.findViewById(R.id.tv_time_ago)
        private val tvOverlayText: TextView = itemView.findViewById(R.id.tv_overlay_text)
        private val ivPlayButton: ImageView = itemView.findViewById(R.id.iv_play_button)
        private val ivShare: ImageView = itemView.findViewById(R.id.iv_share)

        fun bind(podcast: PodcastItem) {
            ivThumbnail.setImageResource(podcast.thumbnail)
            tvTitle.text = podcast.title
            tvSource.text = podcast.source
            tvTimeAgo.text = podcast.timeAgo
            tvOverlayText.text = podcast.overlayText
        }
    }
}
