package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R

class TimeSlotAdapter(
    private val timeSlots: List<String>,
    private val onTimeSlotSelected: (String) -> Unit
) : RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder>() {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeSlotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time_slot, parent, false)
        return TimeSlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeSlotViewHolder, position: Int) {
        val timeSlot = timeSlots[position]
        holder.bind(timeSlot, position == selectedPosition)
    }

    override fun getItemCount(): Int = timeSlots.size

    inner class TimeSlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val timeSlotText: TextView = itemView.findViewById(R.id.tv_time_slot)
        private val radioButton: RadioButton = itemView.findViewById(R.id.rb_time_slot)

        fun bind(timeSlot: String, isSelected: Boolean) {
            timeSlotText.text = timeSlot
            radioButton.isChecked = isSelected

            itemView.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onTimeSlotSelected(timeSlot)
            }
        }
    }
}
