package com.morbidelli.morbidelli_design.model

import java.io.Serializable

data class LocationModel(
    val id: Int,
    val dealershipName: String,
    val address: String,
    val fromDate: String,
    val latitude: Double,
    val longitude: Double,
    val isSelected: Boolean = false,
    val isExpanded: Boolean = false,
    val availableMotorbikes: Int = 1,
    val workingHours: WorkingHours? = null,
    val contactInfo: ContactInfo? = null
) : Serializable

data class WorkingHours(
    val weekdays: String,
    val weekend: String
) : Serializable

data class ContactInfo(
    val phoneNumber: String,
    val email: String
) : Serializable
