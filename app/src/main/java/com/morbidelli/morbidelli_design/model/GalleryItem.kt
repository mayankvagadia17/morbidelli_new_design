package com.morbidelli.morbidelli_design.model

data class GalleryItem(
    val id: Int,
    val type: GalleryItemType,
    val resourceId: Int, // Drawable resource ID for image or video thumbnail
    val videoDuration: String? = null // Only for video type, e.g., "0:16"
)

enum class GalleryItemType {
    IMAGE,
    VIDEO
}
