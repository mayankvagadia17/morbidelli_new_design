package com.morbidelli.morbidelli_design.model

import java.io.Serializable

data class LocationModel(
    val id: Int,
    val dealershipName: String,
    val address: String,
    val fromDate: String,
    val latitude: Double,
    val longitude: Double,
    val isSelected: Boolean = false
) : Serializable
