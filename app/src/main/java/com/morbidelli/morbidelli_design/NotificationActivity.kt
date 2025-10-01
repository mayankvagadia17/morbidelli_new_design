package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvBadge: TextView
    private lateinit var tvMarkAllRead: TextView
    private lateinit var rvNotifications: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter

    private val notifications = listOf(
        NotificationItem(
            id = "1",
            title = "Booking Confirmed",
            message = "Your Yamaha MT-07 rental for tomorrow has been confirmed. Pickup time: 9:00 AM",
            actionText = "View booking details",
            timestamp = "2 hours ago",
            isRead = false
        ),
        NotificationItem(
            id = "2",
            title = "Pickup Reminder",
            message = "Don't forget to bring your driving license and helmet for your rental pickup tomorrow.",
            timestamp = "5 hours ago",
            isRead = false
        ),
        NotificationItem(
            id = "3",
            title = "Return Completed",
            message = "Thank you for returning the Kawasaki Ninja 400 in perfect condition. Your deposit has been released.",
            actionText = "Leave Review",
            timestamp = "1 week ago",
            isRead = true
        ),
        NotificationItem(
            id = "4",
            title = "Booking Confirmed",
            message = "Your Yamaha Ninja 400 rental for tomorrow has been confirmed. Pickup time: 9:00 AM",
            actionText = "View booking details",
            timestamp = "2 weeks ago",
            isRead = true
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        tvBadge = findViewById(R.id.tv_badge)
        tvMarkAllRead = findViewById(R.id.tv_mark_all_read)
        rvNotifications = findViewById(R.id.rv_notifications)
    }
    
    private fun setupRecyclerView() {
        notificationAdapter = NotificationAdapter(notifications) { notification ->
            onNotificationClick(notification)
        }
        
        rvNotifications.layoutManager = LinearLayoutManager(this)
        rvNotifications.adapter = notificationAdapter
        
        // Add dividers between items
        val dividerItemDecoration = androidx.recyclerview.widget.DividerItemDecoration(
            this,
            androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
        )
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider_line)!!)
        rvNotifications.addItemDecoration(dividerItemDecoration)
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        tvMarkAllRead.setOnClickListener {
            markAllAsRead()
        }
    }
    
    private fun onNotificationClick(notification: NotificationItem) {
        // Handle notification click
        Toast.makeText(this, "Clicked: ${notification.title}", Toast.LENGTH_SHORT).show()
        
        // Mark as read if not already read
        if (!notification.isRead) {
            // In a real app, you would update the database here
            // For now, we'll just show a toast
            Toast.makeText(this, "Marked as read", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun markAllAsRead() {
        // In a real app, you would update all notifications as read in the database
        // For now, we'll just show a toast
        Toast.makeText(this, "All notifications marked as read", Toast.LENGTH_SHORT).show()
        tvBadge.visibility = View.GONE
    }
}
