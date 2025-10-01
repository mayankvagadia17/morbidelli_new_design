package com.morbidelli.morbidelli_design

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessagesListAdapter(
    private val messages: List<MessageItem>,
    private val onMessageClick: (MessageItem) -> Unit
) : RecyclerView.Adapter<MessagesListAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleThumbnail: ImageView = itemView.findViewById(R.id.iv_vehicle_thumbnail)
        val date: TextView = itemView.findViewById(R.id.tv_date)
        val time: TextView = itemView.findViewById(R.id.tv_time)
        val vehicleInfo: TextView = itemView.findViewById(R.id.tv_vehicle_info)
        val dealerLogo: ImageView = itemView.findViewById(R.id.iv_dealer_logo)
        val dealerName: TextView = itemView.findViewById(R.id.tv_dealer_name)
        val indicatorDot: View = itemView.findViewById(R.id.indicator_dot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        
        holder.date.text = message.date
        holder.time.text = message.time
        holder.vehicleInfo.text = message.vehicleInfo
        holder.dealerName.text = message.dealerName
        
        // Set dealer logo based on dealer name
        when (message.dealerName) {
            "Oakley Motorcycles" -> holder.dealerLogo.setImageResource(R.drawable.ic_oakley_logo)
            "H&R Block Motorcycles" -> holder.dealerLogo.setImageResource(R.drawable.ic_hr_block_logo)
            else -> holder.dealerLogo.setImageResource(R.drawable.ic_oakley_logo)
        }
        
        // Show/hide unread indicator
        holder.indicatorDot.visibility = if (message.hasUnreadIndicator) View.VISIBLE else View.GONE
        
        // Set click listener
        holder.itemView.setOnClickListener {
            onMessageClick(message)
        }
    }

    override fun getItemCount(): Int = messages.size
}
