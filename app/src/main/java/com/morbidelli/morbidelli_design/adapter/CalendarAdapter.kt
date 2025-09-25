package com.morbidelli.morbidelli_design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R

class CalendarAdapter(
    private val dates: List<String>,
    private val onDateSelected: (String) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DateViewHolder>() {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_date, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val date = dates[position]
        holder.bind(date, position == selectedPosition)
    }

    override fun getItemCount(): Int = dates.size

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateText: TextView = itemView.findViewById(R.id.tv_date)

        fun bind(date: String, isSelected: Boolean) {
            if (date.isEmpty()) {
                dateText.visibility = View.GONE
                dateText.text = ""
            } else {
                dateText.visibility = View.VISIBLE
                dateText.text = date
                dateText.setTextColor(android.graphics.Color.BLACK)
                dateText.textSize = 18f
                dateText.setTypeface(null, android.graphics.Typeface.BOLD)
                
                // Check if date is available
                val availableDates = listOf("3", "10", "17", "24", "28", "29", "31")
                if (availableDates.contains(date)) {
                    dateText.setTextColor(android.graphics.Color.BLUE)
                }
            }

            itemView.setOnClickListener {
                if (date.isNotEmpty()) {
                    val previousPosition = selectedPosition
                    selectedPosition = adapterPosition
                    notifyItemChanged(previousPosition)
                    notifyItemChanged(selectedPosition)
                    onDateSelected(date)
                }
            }
        }
    }
}
