package com.morbidelli.morbidelli_design.model

import java.io.Serializable

data class Booking(
    val id: String,
    val date: String,
    val time: String,
    val vehicleModel: String,
    val vehicleCode: String,
    val companyName: String,
    val address: String,
    val status: BookingStatus,
    val hasRescheduleOption: Boolean = true,
    val hasCancelOption: Boolean = true,
    val contactPerson: String = "",
    val contactEmail: String = "",
    val vehicleImage: String = "",
    val transmission: String = "",
    val color: String = "",
    val timezone: String = "GMT+8",
    val recommendation: String = ""
) : Serializable

enum class BookingStatus : Serializable {
    UPCOMING,
    COMPLETED,
    CANCELLED
}

data class Company(
    val name: String,
    val address: String,
    val logoColor: String
)
