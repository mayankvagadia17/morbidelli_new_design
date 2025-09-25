package com.morbidelli.morbidelli_design.model

import java.io.Serializable

data class Message(
    val id: String,
    val text: String,
    val timestamp: Long,
    val isFromUser: Boolean,
    val quotedMessage: QuotedMessage? = null
) : Serializable

data class QuotedMessage(
    val sender: String,
    val message: String
) : Serializable
