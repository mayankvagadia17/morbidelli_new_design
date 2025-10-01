package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessagesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvBadge: TextView
    private lateinit var tvMarkAllRead: TextView
    private lateinit var rvMessages: RecyclerView
    private lateinit var messageAdapter: MessagesListAdapter

    private val messages = listOf(
        MessageItem(
            id = "1",
            date = "Oct 04, 2023",
            time = "10:00 - 10:30 AM",
            vehicleInfo = "C1002V - Naked - #TR-48291",
            dealerName = "Oakley Motorcycles",
            dealerLogo = "ic_oakley_logo",
            isUnread = true,
            hasUnreadIndicator = true
        ),
        MessageItem(
            id = "2",
            date = "Oct 03, 2023",
            time = "12:00 - 12:45 AM",
            vehicleInfo = "C1002V - Naked - #TR-48291",
            dealerName = "H&R Block Motorcycles",
            dealerLogo = "ic_hr_block_logo",
            isUnread = false,
            hasUnreadIndicator = false
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)
        
        initViews()
        setupRecyclerView()
        setupClickListeners()
    }
    
    private fun initViews() {
        btnBack = findViewById(R.id.btn_back)
        tvBadge = findViewById(R.id.tv_badge)
        tvMarkAllRead = findViewById(R.id.tv_mark_all_read)
        rvMessages = findViewById(R.id.rv_messages)
    }
    
    private fun setupRecyclerView() {
        messageAdapter = MessagesListAdapter(messages) { message ->
            onMessageClick(message)
        }
        
        rvMessages.layoutManager = LinearLayoutManager(this)
        rvMessages.adapter = messageAdapter
    }
    
    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        tvMarkAllRead.setOnClickListener {
            markAllAsRead()
        }
        
        // Add underline to "Mark all as read" text
        tvMarkAllRead.paintFlags = tvMarkAllRead.paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG
    }
    
    private fun onMessageClick(message: MessageItem) {
        // Handle message click - navigate to chat
        Toast.makeText(this, "Opening chat with ${message.dealerName}", Toast.LENGTH_SHORT).show()
        
        // In a real app, you would navigate to the chat activity here
        // startActivity(Intent(this, ChatActivity::class.java).apply {
        //     putExtra("dealer_name", message.dealerName)
        //     putExtra("vehicle_info", message.vehicleInfo)
        // })
    }
    
    private fun markAllAsRead() {
        // In a real app, you would update all messages as read in the database
        // For now, we'll just show a toast and hide the badge
        Toast.makeText(this, "All messages marked as read", Toast.LENGTH_SHORT).show()
        tvBadge.visibility = View.GONE
    }
}
