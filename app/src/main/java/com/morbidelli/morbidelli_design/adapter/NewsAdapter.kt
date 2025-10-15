package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.NewsModel

class NewsAdapter(
    private val newsList: List<NewsModel>,
    private val onItemClick: (NewsModel) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivNewsImage: ImageView = itemView.findViewById(R.id.iv_news_image)
        private val rvTags: RecyclerView = itemView.findViewById(R.id.rv_tags)
        private val tvNewsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        private val tvNewsDescription: TextView = itemView.findViewById(R.id.tv_news_description)
        private val tvNewsDate: TextView = itemView.findViewById(R.id.tv_news_date)
        private val tvViewDetails: TextView = itemView.findViewById(R.id.tv_view_details)

        fun bind(news: NewsModel) {
            // Set image (you can use Glide or Picasso for loading from URL)
            // For now, using a placeholder
            ivNewsImage.setImageResource(R.drawable.ic_bike_n300)
            
            // Setup tags RecyclerView
            setupTagsRecyclerView(news)
            
            // Set title and description
            tvNewsTitle.text = news.title
            tvNewsDescription.text = news.description
            
            // Set date
            tvNewsDate.text = news.date
            
            // Set click listener
            itemView.setOnClickListener {
                onItemClick(news)
            }
        }

        private fun setupTagsRecyclerView(news: NewsModel) {
            // Create tags list - add "New" if isNew is true, then add other tags
            val tagsList = mutableListOf<String>()
            if (news.isNew) {
                tagsList.add("New")
            }
            tagsList.addAll(news.tags)
            
            // Setup RecyclerView for tags
            val tagAdapter = TagAdapter(tagsList)
            rvTags.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            rvTags.adapter = tagAdapter
        }
    }
}
