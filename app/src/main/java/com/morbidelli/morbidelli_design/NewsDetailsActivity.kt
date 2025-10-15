package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.NewsAdapter
import com.morbidelli.morbidelli_design.model.NewsModel

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ivMainImage: ImageView
    private lateinit var tvDate: TextView
    private lateinit var rvTags: RecyclerView
    private lateinit var tvArticleTitle: TextView
    private lateinit var tvArticleContent: TextView
    private lateinit var ivArticleImage1: ImageView
    private lateinit var tvSectionTitle: TextView
    private lateinit var tvModel1Title: TextView
    private lateinit var tvModel1Description: TextView
    private lateinit var ivModel1Image: ImageView
    private lateinit var tvModel2Title: TextView
    private lateinit var tvModel2Description: TextView
    private lateinit var ivModel2Image: ImageView
    private lateinit var tvModel3Title: TextView
    private lateinit var tvModel3Description: TextView
    private lateinit var ivModel3Image: ImageView
    private lateinit var tvConclusionTitle: TextView
    private lateinit var tvConclusionContent: TextView
    private lateinit var ivConclusionImage: ImageView
    private lateinit var tvContactTitle: TextView
    private lateinit var tvContactSubtitle: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvWebsite: TextView
    private lateinit var tvSimilarNewsTitle: TextView
    private lateinit var rvSimilarNews: RecyclerView

    private lateinit var similarNewsAdapter: NewsAdapter
    private lateinit var newsModel: NewsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        setupViews()
        setupClickListeners()
        loadNewsData()
        setupSimilarNews()
    }

    private fun setupViews() {
        ivBack = findViewById(R.id.iv_back)
        tvTitle = findViewById(R.id.tv_title)
        ivMainImage = findViewById(R.id.iv_main_image)
        tvDate = findViewById(R.id.tv_date)
        rvTags = findViewById(R.id.rv_tags)
        tvArticleTitle = findViewById(R.id.tv_article_title)
        tvArticleContent = findViewById(R.id.tv_article_content)
        ivArticleImage1 = findViewById(R.id.iv_article_image1)
        tvSectionTitle = findViewById(R.id.tv_section_title)
        tvModel1Title = findViewById(R.id.tv_model1_title)
        tvModel1Description = findViewById(R.id.tv_model1_description)
        ivModel1Image = findViewById(R.id.iv_model1_image)
        tvModel2Title = findViewById(R.id.tv_model2_title)
        tvModel2Description = findViewById(R.id.tv_model2_description)
        ivModel2Image = findViewById(R.id.iv_model2_image)
        tvModel3Title = findViewById(R.id.tv_model3_title)
        tvModel3Description = findViewById(R.id.tv_model3_description)
        ivModel3Image = findViewById(R.id.iv_model3_image)
        tvConclusionTitle = findViewById(R.id.tv_conclusion_title)
        tvConclusionContent = findViewById(R.id.tv_conclusion_content)
        ivConclusionImage = findViewById(R.id.iv_conclusion_image)
        tvContactTitle = findViewById(R.id.tv_contact_title)
        tvContactSubtitle = findViewById(R.id.tv_contact_subtitle)
        tvEmail = findViewById(R.id.tv_email)
        tvWebsite = findViewById(R.id.tv_website)
        tvSimilarNewsTitle = findViewById(R.id.tv_similar_news_title)
        rvSimilarNews = findViewById(R.id.rv_similar_news)
    }

    private fun setupClickListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        tvEmail.setOnClickListener {
            // Handle email click
        }

        tvWebsite.setOnClickListener {
            // Handle website click
        }
    }

    private fun loadNewsData() {
        // Get news data from intent or create sample data
        newsModel = intent.getParcelableExtra("news") ?: getSampleNewsData()
        
        // Populate views with news data
        tvTitle.text = "News details"
        ivMainImage.setImageResource(R.drawable.ic_bike_n300)
        tvDate.text = newsModel.date
        
        // Setup tags RecyclerView
        setupTagsRecyclerView()
        
        tvArticleTitle.text = newsModel.title
        tvArticleContent.text = getArticleContent()
        ivArticleImage1.setImageResource(R.drawable.ic_bike_n300)
        
        tvSectionTitle.text = "The protagonists of the test ride"
        
        tvModel1Title.text = "Morbidelli F352"
        tvModel1Description.text = "An aggressive streetfighter with a twin-cylinder engine and a rebellious spirit, designed for a new generation of connected and demanding riders."
        ivModel1Image.setImageResource(R.drawable.ic_bike_n300)
        
        tvModel2Title.text = "Morbidelli T352X"
        tvModel2Description.text = "The brand's first true adventure bike, versatile, connected, and built for those who dream of living their great adventure on two wheels."
        ivModel2Image.setImageResource(R.drawable.ic_bike_n300)
        
        tvModel3Title.text = "Morbidelli T502X"
        tvModel3Description.text = "The most complete mid-size adventure bike, striking the perfect balance of Italian design, agility, and performance, delivering excitement on and off the road."
        ivModel3Image.setImageResource(R.drawable.ic_bike_n300)
        
        tvConclusionTitle.text = "A new era for Morbidelli in Italy"
        tvConclusionContent.text = "With this event, Morbidelli not only officially introduced its renewed mid-size range, but also reaffirmed its commitment to the Italian market by offering high-level motorcycles within the premium segment, now available through the brand's official network."
        ivConclusionImage.setImageResource(R.drawable.ic_bike_n300)
        
        tvContactTitle.text = "For more information, please contact:"
        tvContactSubtitle.text = "Morbidelli Motorcycle Communications Team"
        tvEmail.text = "press@morbidelli.com"
        tvWebsite.text = "www.morbidelli.com"
        
        tvSimilarNewsTitle.text = "Similar news"
    }

    private fun setupTagsRecyclerView() {
        val tagsList = mutableListOf<String>()
        if (newsModel.isNew) {
            tagsList.add("New")
        }
        tagsList.addAll(newsModel.tags)
        
        val tagAdapter = com.morbidelli.morbidelli_design.adapter.TagAdapter(tagsList)
        rvTags.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTags.adapter = tagAdapter
    }

    private fun setupSimilarNews() {
        val similarNewsList = getSimilarNewsData()
        similarNewsAdapter = NewsAdapter(similarNewsList) { news ->
            // Handle similar news click - navigate to details
            // For now, just finish current activity
        }
        
        rvSimilarNews.layoutManager = LinearLayoutManager(this)
        rvSimilarNews.adapter = similarNewsAdapter
    }

    private fun getArticleContent(): String {
        return "Morbidelli recently hosted the exclusive 3x3 Test Ride, a press-only event held at Pogliani, Sesto San Giovanni (Milan). Over three consecutive days, leading motorcycle journalists had the chance to get on the saddle of the new F352, T352X and T502X models, introduced for the very first time to the Italian market.\n\n" +
                "The event, which took place from September 8 to 10, began with a welcome coffee and a technical presentation delivered by the Morbidelli team, highlighting the brand's vision and the key features of its new mid-size range. Unlike traditional test rides, the \"three bikes, three days, one unhurried experience\" format allowed participants to enjoy extended riding sessions, moving beyond the usual short stints and gaining a deeper understanding of each model's performance."
    }

    private fun getSampleNewsData(): NewsModel {
        return NewsModel(
            id = 1,
            title = "Morbidelli 3x3 Test Ride: Three Days of Riding with the New Mid-Size Range",
            description = "Morbidelli recently hosted the exclusive 3x3 Test Ride, a press-only event held at Pogliani, Sesto San Giovanni (Milan).",
            imageUrl = "",
            date = "Sep 12, 2024",
            category = "Morbidelli",
            tags = listOf("New", "Morbidelli", "Beyond the ride", "F352"),
            isNew = true
        )
    }

    private fun getSimilarNewsData(): List<NewsModel> {
        return listOf(
            NewsModel(
                id = 2,
                title = "Unveiling Innovation: The Latest Offerings from MBP Moto",
                description = "Keeway is excited to announce the launch of our new website, providing the latest features and services to our community...",
                imageUrl = "",
                date = "June 24",
                category = "Keeway",
                tags = listOf("New", "Keeway"),
                isNew = true
            ),
            NewsModel(
                id = 3,
                title = "New Motorcycle Models Coming Soon",
                description = "Discover the latest additions to our motorcycle lineup with cutting-edge technology and design...",
                imageUrl = "",
                date = "June 20",
                category = "Models",
                tags = listOf("Models", "New"),
                isNew = true
            )
        )
    }
}
