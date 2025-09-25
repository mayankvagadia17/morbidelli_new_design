package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.Message
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val messages: MutableList<Message>,
    private val onMessageSwipe: (Message) -> Unit
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int = messages.size

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val llSenderMessage: LinearLayout = itemView.findViewById(R.id.ll_sender_message)
        private val llReceiverMessage: LinearLayout = itemView.findViewById(R.id.ll_receiver_message)
        private val tvMessageText: TextView = itemView.findViewById(R.id.tv_message_text)
        private val tvReceiverText: TextView = itemView.findViewById(R.id.tv_receiver_text)
        private val tvMessageTime: TextView = itemView.findViewById(R.id.tv_message_time)
        private val tvReceiverTime: TextView = itemView.findViewById(R.id.tv_receiver_time)
        private val ivMessageStatus: ImageView = itemView.findViewById(R.id.iv_message_status)
        private val ivReceiverStatus: ImageView = itemView.findViewById(R.id.iv_receiver_status)
        private val llQuotedMessage: LinearLayout = itemView.findViewById(R.id.ll_quoted_message)
        private val tvQuotedSender: TextView = itemView.findViewById(R.id.tv_quoted_sender)
        private val tvQuotedMessage: TextView = itemView.findViewById(R.id.tv_quoted_message)

        fun bind(message: Message) {
            // Show/hide sender or receiver message
            if (message.isFromUser) {
                llSenderMessage.visibility = View.VISIBLE
                llReceiverMessage.visibility = View.GONE
                tvMessageText.text = message.text
                tvMessageTime.text = formatTime(message.timestamp)
            } else {
                llSenderMessage.visibility = View.GONE
                llReceiverMessage.visibility = View.VISIBLE
                tvReceiverText.text = message.text
                tvReceiverTime.text = formatTime(message.timestamp)
            }

            // Handle quoted message
            if (message.quotedMessage != null) {
                llQuotedMessage.visibility = View.VISIBLE
                tvQuotedSender.text = message.quotedMessage.sender
                tvQuotedMessage.text = message.quotedMessage.message
            } else {
                llQuotedMessage.visibility = View.GONE
            }

            // Set up swipe gesture
            itemView.setOnTouchListener { _, event ->
                // Simple swipe detection - you can enhance this with GestureDetector
                if (event.action == android.view.MotionEvent.ACTION_DOWN) {
                    // Start tracking swipe
                    true
                } else if (event.action == android.view.MotionEvent.ACTION_UP) {
                    // Check if it was a swipe and call callback
                    onMessageSwipe(message)
                    true
                } else {
                    false
                }
            }
        }

        private fun formatTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            return sdf.format(Date(timestamp))
        }
    }
}
