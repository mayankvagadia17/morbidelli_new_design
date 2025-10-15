package com.morbidelli.morbidelli_design.model

data class NewsModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val date: String,
    val category: String,
    val tags: List<String>,
    val isNew: Boolean = false
)
