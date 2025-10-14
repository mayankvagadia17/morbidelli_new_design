package com.morbidelli.morbidelli_design.model

data class MotorcycleModel(
    val id: Int,
    val name: String,
    val imageResource: Int,
    val category: String,
    val priceFrom: String,
    val power: String,
    val curbWeight: String,
    val maxTorque: String
)
