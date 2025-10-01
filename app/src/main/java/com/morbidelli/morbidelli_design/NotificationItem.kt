package com.morbidelli.morbidelli_design

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val actionText: String? = null,
    val timestamp: String,
    val isRead: Boolean = false,
    val actionUrl: String? = null
)
