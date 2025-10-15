package com.morbidelli.morbidelli_design.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.NewsDetailsActivity
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.adapter.NewsAdapter
import com.morbidelli.morbidelli_design.model.NewsModel

class NewsFragment : Fragment() {

    private lateinit var rvNews: RecyclerView
    private lateinit var llEmptyState: LinearLayout
    private lateinit var tvEmptyTitle: TextView
    private lateinit var tvEmptySubtitle: TextView
    private lateinit var btnFilter: Button
    private lateinit var newsAdapter: NewsAdapter

    private val newsList = mutableListOf<NewsModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViews(view)
        setupRecyclerView()
        setupClickListeners()
        loadNewsData()
    }

    private fun setupViews(view: View) {
        rvNews = view.findViewById(R.id.rv_news)
        llEmptyState = view.findViewById(R.id.ll_empty_state)
        tvEmptyTitle = view.findViewById(R.id.tv_empty_title)
        tvEmptySubtitle = view.findViewById(R.id.tv_empty_subtitle)
        btnFilter = view.findViewById(R.id.btn_filter)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(newsList) { news ->
            // Handle news item click
            onNewsItemClick(news)
        }
        
        rvNews.layoutManager = LinearLayoutManager(context)
        rvNews.adapter = newsAdapter
    }

    private fun setupClickListeners() {
        btnFilter.setOnClickListener {
            // Handle filter button click
            onFilterClick()
        }
    }

    private fun loadNewsData() {
        // Sample news data - replace with actual data loading
        newsList.clear()
        newsList.addAll(getSampleNewsData())
        
        newsAdapter.notifyDataSetChanged()
        updateEmptyState()
    }

    private fun getSampleNewsData(): List<NewsModel> {
        return listOf(
            NewsModel(
                id = 1,
                title = "Unveiling Innovation: The Latest Offerings from MBP Moto",
                description = "Keeway is excited to announce the launch of our new website, providing the latest features and services to our community...",
                imageUrl = "",
                date = "June 24",
                category = "Keeway",
                tags = listOf("New", "Keeway"),
                isNew = true
            ),
            NewsModel(
                id = 2,
                title = "New Motorcycle Models Coming Soon",
                description = "Discover the latest additions to our motorcycle lineup with cutting-edge technology and design...",
                imageUrl = "",
                date = "June 20",
                category = "Models",
                tags = listOf("Models", "New"),
                isNew = true
            ),
            NewsModel(
                id = 3,
                title = "Test Ride Experience Program",
                description = "Book your test ride today and experience the thrill of our premium motorcycles...",
                imageUrl = "",
                date = "June 18",
                category = "Test Ride",
                tags = listOf("Test Ride", "Experience"),
                isNew = false
            ),
            NewsModel(
                id = 4,
                title = "Beyond the Ride: Community Events",
                description = "Join our community events and connect with fellow motorcycle enthusiasts...",
                imageUrl = "",
                date = "June 15",
                category = "Community",
                tags = listOf("Community", "Events"),
                isNew = false
            )
        )
    }

    private fun updateEmptyState() {
        if (newsList.isEmpty()) {
            rvNews.visibility = View.GONE
            llEmptyState.visibility = View.VISIBLE
        } else {
            rvNews.visibility = View.VISIBLE
            llEmptyState.visibility = View.GONE
        }
    }

    private fun onNewsItemClick(news: NewsModel) {
        // Navigate to news details screen
        val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
        intent.putExtra("news", news)
        startActivity(intent)
    }

    private fun onFilterClick() {
        // Handle filter button click
        // You can implement filter functionality here
        // For now, just show a toast
        // Toast.makeText(context, "Filter clicked", Toast.LENGTH_SHORT).show()
    }
}
