package com.morbidelli.morbidelli_design.model

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
    val hasCancelOption: Boolean = true
)

enum class BookingStatus {
    UPCOMING,
    COMPLETED,
    CANCELLED
}

data class Company(
    val name: String,
    val address: String,
    val logoColor: String
)
