package com.morbidelli.morbidelli_design

data class MessageItem(
    val id: String,
    val date: String,
    val time: String,
    val vehicleInfo: String,
    val dealerName: String,
    val dealerLogo: String, // Resource name for logo
    val isUnread: Boolean = false,
    val hasUnreadIndicator: Boolean = false
)
