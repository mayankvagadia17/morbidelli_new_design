package com.morbidelli.morbidelli_design

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(
    private val notifications: List<NotificationItem>,
    private val onNotificationClick: (NotificationItem) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val indicatorDot: View = itemView.findViewById(R.id.indicator_dot)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val message: TextView = itemView.findViewById(R.id.tv_message)
        val action: TextView = itemView.findViewById(R.id.tv_action)
        val timestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        
        holder.title.text = notification.title
        holder.message.text = notification.message
        holder.timestamp.text = notification.timestamp
        
        // Show/hide unread indicator
        holder.indicatorDot.visibility = if (notification.isRead) View.GONE else View.VISIBLE
        
        // Show/hide action link
        if (notification.actionText != null) {
            holder.action.text = notification.actionText
            holder.action.visibility = View.VISIBLE
        } else {
            holder.action.visibility = View.GONE
        }
        
        // Set click listener
        holder.itemView.setOnClickListener {
            onNotificationClick(notification)
        }
    }

    override fun getItemCount(): Int = notifications.size
}
