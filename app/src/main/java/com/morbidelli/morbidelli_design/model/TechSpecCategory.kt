package com.morbidelli.morbidelli_design.model

data class TechSpecCategory(
    val id: Int,
    val title: String,
    val isExpanded: Boolean,
    val specifications: List<Pair<String, String>>
)
