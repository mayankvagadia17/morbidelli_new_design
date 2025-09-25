package com.morbidelli.morbidelli_design

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.adapter.MessageAdapter
import com.morbidelli.morbidelli_design.model.Booking
import com.morbidelli.morbidelli_design.model.Message
import com.morbidelli.morbidelli_design.model.QuotedMessage
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageView
    private lateinit var btnAttachment: ImageView
    private lateinit var etMessage: EditText
    private lateinit var btnEmoji: ImageView
    private lateinit var btnSend: ImageView
    private lateinit var rvMessages: RecyclerView
    private lateinit var llReply: LinearLayout
    private lateinit var tvReplySender: TextView
    private lateinit var tvReplyMessage: TextView
    private lateinit var btnCloseReply: ImageView

    private var booking: Booking? = null
    private lateinit var messageAdapter: MessageAdapter
    private val messages = mutableListOf<Message>()
    private var currentReplyMessage: Message? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Get booking data from intent
        booking = intent.getSerializableExtra("booking") as? Booking

        setupViews()
        setupRecyclerView()
        setupButtonListeners()
        setupMessageInput()
        loadSampleMessages()
    }

    private fun setupViews() {
        btnBack = findViewById(R.id.btn_back)
        btnAttachment = findViewById(R.id.btn_attachment)
        etMessage = findViewById(R.id.et_message)
        btnEmoji = findViewById(R.id.btn_emoji)
        btnSend = findViewById(R.id.btn_send)
        rvMessages = findViewById(R.id.rv_messages)
        llReply = findViewById(R.id.ll_reply)
        tvReplySender = findViewById(R.id.tv_reply_sender)
        tvReplyMessage = findViewById(R.id.tv_reply_message)
        btnCloseReply = findViewById(R.id.btn_close_reply)
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(messages) { message ->
            // Handle message swipe for reply
            showReplyToMessage(message)
        }
        rvMessages.layoutManager = LinearLayoutManager(this)
        rvMessages.adapter = messageAdapter
    }

    private fun setupButtonListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnAttachment.setOnClickListener {
            // Handle attachment functionality
            // You can add file picker or camera functionality here
        }

        btnEmoji.setOnClickListener {
            // Handle emoji picker
            // You can add emoji picker functionality here
        }

        btnSend.setOnClickListener {
            sendMessage()
        }

        btnCloseReply.setOnClickListener {
            hideReply()
        }
    }

    private fun setupMessageInput() {
        etMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Enable/disable send button based on text input
                val hasText = s?.toString()?.trim()?.isNotEmpty() ?: false
                btnSend.isEnabled = hasText
                btnSend.alpha = if (hasText) 1.0f else 0.5f
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun sendMessage() {
        val message = etMessage.text.toString().trim()
        if (message.isNotEmpty()) {
            val newMessage = Message(
                id = UUID.randomUUID().toString(),
                text = message,
                timestamp = System.currentTimeMillis(),
                isFromUser = true,
                quotedMessage = if (currentReplyMessage != null) {
                    QuotedMessage(
                        sender = if (currentReplyMessage!!.isFromUser) "You" else "Oakley Motorcycles",
                        message = currentReplyMessage!!.text
                    )
                } else null
            )
            
            messages.add(newMessage)
            messageAdapter.notifyItemInserted(messages.size - 1)
            rvMessages.scrollToPosition(messages.size - 1)
            
            etMessage.text.clear()
            hideReply()
        }
    }

    private fun showReplyToMessage(message: Message) {
        currentReplyMessage = message
        tvReplySender.text = "Reply to ${if (message.isFromUser) "You" else "Oakley Motorcycles"}"
        tvReplyMessage.text = message.text
        llReply.visibility = View.VISIBLE
    }

    private fun hideReply() {
        currentReplyMessage = null
        llReply.visibility = View.GONE
    }

    private fun loadSampleMessages() {
        // Add sample messages to demonstrate the chat
        val sampleMessages = listOf(
            Message(
                id = "1",
                text = "My message goes here... Example text goes like this test text here",
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                isFromUser = false
            ),
            Message(
                id = "2",
                text = "Remarkable service offered.",
                timestamp = System.currentTimeMillis() - 1800000, // 30 minutes ago
                isFromUser = true,
                quotedMessage = QuotedMessage(
                    sender = "You",
                    message = "My message goes here... Example tex..."
                )
            )
        )
        
        messages.addAll(sampleMessages)
        messageAdapter.notifyDataSetChanged()
    }
}
