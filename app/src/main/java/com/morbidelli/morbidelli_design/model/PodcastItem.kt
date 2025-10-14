package com.morbidelli.morbidelli_design.model

data class PodcastItem(
    val id: Int,
    val title: String,
    val thumbnail: Int,
    val source: String,
    val timeAgo: String,
    val overlayText: String
)
