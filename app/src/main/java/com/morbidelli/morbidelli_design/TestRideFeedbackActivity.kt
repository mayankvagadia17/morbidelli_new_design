package com.morbidelli.morbidelli_design

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

data class UploadedImage(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val size: String,
    val date: String,
    val thumbnail: Bitmap?,
    val originalUri: Uri?,
    val localPath: String?,
    val fileSize: Long = 0
)

class TestRideFeedbackActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog
    private lateinit var currentView: View
    private val uploadedImages = mutableListOf<UploadedImage>()
    
    // Form views
    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etFeedback: EditText
    private lateinit var llUploadArea: LinearLayout
    private lateinit var rvUploadedImages: RecyclerView
    private lateinit var btnContinue: Button
    private lateinit var imageAdapter: ImageAdapter
    
    // Rating views
    private lateinit var btnBack: Button
    private lateinit var btnSubmit: Button
    private val ratings = mutableMapOf<String, Int>() // category -> rating (1-5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Use your main activity layout
        
        showFeedbackDialog()
    }

    private fun showFeedbackDialog() {
        dialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar)
        dialog.setContentView(R.layout.bottom_sheet_test_ride_feedback)
        
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setGravity(android.view.Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomSheetAnimation)
        
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        
        currentView = dialog.window?.decorView?.findViewById(android.R.id.content)!!
        setupFormViews()
        dialog.show()
    }

    private fun setupFormViews() {
        etFirstName = dialog.findViewById(R.id.et_first_name)
        etLastName = dialog.findViewById(R.id.et_last_name)
        etFeedback = dialog.findViewById(R.id.et_feedback)
        llUploadArea = dialog.findViewById(R.id.ll_upload_area)
        rvUploadedImages = dialog.findViewById(R.id.rv_uploaded_images)
        btnContinue = dialog.findViewById(R.id.btn_continue)
        
        // Setup RecyclerView
        setupImageRecyclerView()
        
        // Set click listeners
        dialog.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }
        
        llUploadArea.setOnClickListener {
            openImagePicker()
        }
        
        btnContinue.setOnClickListener {
            if (validateForm()) {
                showRatingDialog()
            }
        }
        
        // Start with empty form
        etFirstName.setText("")
        etLastName.setText("")
        etFeedback.setText("")
    }

    private fun setupImageRecyclerView() {
        imageAdapter = ImageAdapter(uploadedImages) { image ->
            removeImage(image)
        }
        
        rvUploadedImages.apply {
            layoutManager = GridLayoutManager(this@TestRideFeedbackActivity, 4)
            adapter = imageAdapter
        }
    }

    private fun updateUploadedImagesView() {
        if (uploadedImages.isNotEmpty()) {
            rvUploadedImages.visibility = View.VISIBLE
            imageAdapter.notifyDataSetChanged()
        } else {
            rvUploadedImages.visibility = View.GONE
        }
    }
    
    private fun addImageToList(image: UploadedImage) {
        val position = uploadedImages.size
        uploadedImages.add(image)
        if (uploadedImages.size == 1) {
            rvUploadedImages.visibility = View.VISIBLE
        }
        imageAdapter.notifyItemInserted(position)
    }
    
    private fun removeImageFromList(image: UploadedImage) {
        val position = uploadedImages.indexOf(image)
        if (position >= 0) {
            uploadedImages.removeAt(position)
            imageAdapter.notifyItemRemoved(position)
            if (uploadedImages.isEmpty()) {
                rvUploadedImages.visibility = View.GONE
            }
        }
    }
    
    private fun removeImage(image: UploadedImage) {
        // Delete local file if exists
        image.localPath?.let { path ->
            try {
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                    Log.d("ImageUpload", "Deleted local file: $path")
                }
            } catch (e: Exception) {
                Log.e("ImageUpload", "Error deleting file: $path", e)
            }
        }
        
        // Remove from list with efficient update
        removeImageFromList(image)
    }
    
    private fun getAllImageUrls(): List<String> {
        return uploadedImages.mapNotNull { image ->
            image.localPath ?: image.originalUri?.toString()
        }
    }
    
    private fun getImageUrlsAsString(): String {
        return getAllImageUrls().joinToString(",")
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true) // Allow multiple image selection
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val clipData = data.clipData
            val imageUri = data.data
            
            if (clipData != null) {
                // Multiple images selected
                for (i in 0 until clipData.itemCount) {
                    val uri = clipData.getItemAt(i).uri
                    processSelectedImage(uri)
                }
            } else if (imageUri != null) {
                // Single image selected
                processSelectedImage(imageUri)
            }
        }
    }
    
    private fun processSelectedImage(uri: Uri) {
        try {
            // Get image information
            val cursor = contentResolver.query(uri, null, null, null, null)
            var fileName = "image_${System.currentTimeMillis()}.jpg"
            var fileSize = 0L
            
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                    val sizeIndex = it.getColumnIndex(MediaStore.Images.Media.SIZE)
                    
                    if (nameIndex >= 0) {
                        fileName = it.getString(nameIndex) ?: fileName
                    }
                    if (sizeIndex >= 0) {
                        fileSize = it.getLong(sizeIndex)
                    }
                }
            }
            
            // Create thumbnail
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val thumbnail = createThumbnail(bitmap)
            
            // Save image to local storage
            val localPath = saveImageToLocal(uri, fileName)
            
            // Format file size
            val formattedSize = formatFileSize(fileSize)
            
            // Create uploaded image object
            val image = UploadedImage(
                name = fileName,
                size = formattedSize,
                date = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault()).format(Date()),
                thumbnail = thumbnail,
                originalUri = uri,
                localPath = localPath,
                fileSize = fileSize
            )
            
            addImageToList(image)
            
            Log.d("ImageUpload", "Image added: $fileName, Size: $formattedSize, Path: $localPath")
            
        } catch (e: Exception) {
            Log.e("ImageUpload", "Error processing image", e)
            Toast.makeText(this, "Error processing image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun createThumbnail(bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) return null
        
        val maxSize = 200
        val width = bitmap.width
        val height = bitmap.height
        
        val scale = if (width > height) {
            maxSize.toFloat() / width
        } else {
            maxSize.toFloat() / height
        }
        
        val scaledWidth = (width * scale).toInt()
        val scaledHeight = (height * scale).toInt()
        
        return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
    }
    
    private fun saveImageToLocal(uri: Uri, fileName: String): String? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val file = File(getExternalFilesDir(null), "uploaded_images")
            if (!file.exists()) {
                file.mkdirs()
            }
            
            val imageFile = File(file, fileName)
            val outputStream = FileOutputStream(imageFile)
            
            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            
            imageFile.absolutePath
        } catch (e: Exception) {
            Log.e("ImageUpload", "Error saving image", e)
            null
        }
    }
    
    private fun formatFileSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)} MB"
            else -> "${bytes / (1024 * 1024 * 1024)} GB"
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        
        if (etFirstName.text.toString().trim().isEmpty()) {
            etFirstName.error = "First name is required"
            isValid = false
        }
        
        if (etLastName.text.toString().trim().isEmpty()) {
            etLastName.error = "Last name is required"
            isValid = false
        }
        
        return isValid
    }

    private fun showRatingDialog() {
        dialog.dismiss()
        
        dialog = Dialog(this, android.R.style.Theme_Material_Light_NoActionBar)
        dialog.setContentView(R.layout.bottom_sheet_test_ride_rating)
        
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setGravity(android.view.Gravity.BOTTOM)
        window?.setWindowAnimations(R.style.BottomSheetAnimation)
        
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        
        currentView = dialog.window?.decorView?.findViewById(android.R.id.content)!!
        setupRatingViews()
        dialog.show()
    }

    private fun setupRatingViews() {
        btnBack = dialog.findViewById(R.id.btn_back)
        btnSubmit = dialog.findViewById(R.id.btn_submit)
        
        // Clear any previous ratings
        ratings.clear()
        
        // Set click listeners
        dialog.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }
        
        btnBack.setOnClickListener {
            dialog.dismiss()
            showFeedbackDialog()
        }
        
        btnSubmit.setOnClickListener {
            if (validateRatings()) {
                submitFeedback()
            }
        }
        
        // Setup star rating listeners
        setupStarRatings()
    }

    private fun setupStarRatings() {
        val categories = listOf("price", "dealer", "motorbike", "punctuality", "satisfaction")
        
        categories.forEach { category ->
            val starIds = when (category) {
                "price" -> listOf(R.id.iv_price_star_1, R.id.iv_price_star_2, R.id.iv_price_star_3, R.id.iv_price_star_4, R.id.iv_price_star_5)
                "dealer" -> listOf(R.id.iv_dealer_star_1, R.id.iv_dealer_star_2, R.id.iv_dealer_star_3, R.id.iv_dealer_star_4, R.id.iv_dealer_star_5)
                "motorbike" -> listOf(R.id.iv_motorbike_star_1, R.id.iv_motorbike_star_2, R.id.iv_motorbike_star_3, R.id.iv_motorbike_star_4, R.id.iv_motorbike_star_5)
                "punctuality" -> listOf(R.id.iv_punctuality_star_1, R.id.iv_punctuality_star_2, R.id.iv_punctuality_star_3, R.id.iv_punctuality_star_4, R.id.iv_punctuality_star_5)
                "satisfaction" -> listOf(R.id.iv_satisfaction_star_1, R.id.iv_satisfaction_star_2, R.id.iv_satisfaction_star_3, R.id.iv_satisfaction_star_4, R.id.iv_satisfaction_star_5)
                else -> emptyList()
            }
            
            starIds.forEachIndexed { index, starId ->
                dialog.findViewById<ImageView>(starId).setOnClickListener {
                    setRating(category, index + 1, starIds)
                }
            }
        }
    }

    private fun setRating(category: String, rating: Int, starIds: List<Int>) {
        ratings[category] = rating
        
        starIds.forEachIndexed { index, starId ->
            val starView = dialog.findViewById<ImageView>(starId)
            starView.setImageResource(
                if (index < rating) R.drawable.ic_star_filled else R.drawable.ic_star_empty
            )
        }
    }

    private fun validateRatings(): Boolean {
        val requiredCategories = listOf("price", "dealer", "motorbike", "punctuality", "satisfaction")
        
        for (category in requiredCategories) {
            if (!ratings.containsKey(category) || ratings[category] == 0) {
                Toast.makeText(this, "Please rate all categories", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        
        return true
    }

    private fun submitFeedback() {
        // Collect all form data
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val feedback = etFeedback.text.toString().trim()
        val imageUrls = getAllImageUrls()
        val imageUrlsString = getImageUrlsAsString()
        
        // Log all collected data
        Log.d("FeedbackSubmission", "First Name: $firstName")
        Log.d("FeedbackSubmission", "Last Name: $lastName")
        Log.d("FeedbackSubmission", "Feedback: $feedback")
        Log.d("FeedbackSubmission", "Image URLs: $imageUrlsString")
        Log.d("FeedbackSubmission", "Ratings: $ratings")
        
        // Here you would typically send the feedback to your backend
        // Example API call:
        // val feedbackData = FeedbackData(
        //     firstName = firstName,
        //     lastName = lastName,
        //     feedback = feedback,
        //     imageUrls = imageUrls,
        //     ratings = ratings
        // )
        // apiService.submitFeedback(feedbackData)
        
        Toast.makeText(this, "Feedback submitted successfully!\nImages: ${imageUrls.size}", Toast.LENGTH_LONG).show()
        dialog.dismiss()
        finish()
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1001
    }
}

class ImageAdapter(
    private val images: MutableList<UploadedImage>,
    private val onDeleteClick: (UploadedImage) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_image_thumbnail)
        val deleteButton: ImageView = itemView.findViewById(R.id.btn_delete_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_uploaded_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        
        // Set image thumbnail
        image.thumbnail?.let { bitmap ->
            holder.imageView.setImageBitmap(bitmap)
        } ?: run {
            holder.imageView.setImageResource(R.drawable.ic_camera)
        }
        
        // Set delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(image)
        }
    }

    override fun getItemCount(): Int = images.size
}
