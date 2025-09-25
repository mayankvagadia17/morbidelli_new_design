package com.morbidelli.morbidelli_design

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.GridLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.morbidelli.morbidelli_design.adapter.CalendarAdapter
import com.morbidelli.morbidelli_design.adapter.TimeSlotAdapter
import java.util.*

class RescheduleBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var calendarGrid: GridLayout
    private lateinit var timeSlotsRecyclerView: RecyclerView
    private lateinit var monthYearText: TextView
    private lateinit var timezoneText: TextView
    private lateinit var switch24h: Switch
    
    private var selectedDate: String = ""
    private var selectedTimeSlot: String = ""
    private var onRescheduleConfirmed: ((String, String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_reschedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViews(view)
        setupCalendar()
        setupTimeSlots()
        setupButtonListeners(view)
    }

    private fun setupViews(view: View) {
        calendarGrid = view.findViewById(R.id.calendar_grid)
        timeSlotsRecyclerView = view.findViewById(R.id.rv_time_slots)
        monthYearText = view.findViewById(R.id.tv_month_year)
        timezoneText = view.findViewById(R.id.tv_timezone)
        switch24h = view.findViewById(R.id.switch_24h)
    }

    private fun setupCalendar() {
        // Generate calendar dates for current month
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)
        
        monthYearText.text = getMonthYearString(currentMonth, currentYear)
        
        // Clear existing views
        calendarGrid.removeAllViews()
        
        // Create calendar dates
        val dates = generateCalendarDates(currentMonth, currentYear)
        
        // Add dates to grid
        for (i in 0 until 42) {
            val dateText = TextView(requireContext())
            dateText.layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                height = 80
                columnSpec = GridLayout.spec(i % 7, 1f)
                rowSpec = GridLayout.spec(i / 7, 1f)
                setMargins(4, 4, 4, 4)
            }
            
            if (i < dates.size && dates[i].isNotEmpty()) {
                dateText.text = dates[i]
                dateText.setTextColor(android.graphics.Color.BLACK)
                dateText.textSize = 16f
                dateText.gravity = android.view.Gravity.CENTER
                dateText.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"))
                dateText.setPadding(8, 8, 8, 8)
                
                // Check if date is available
                val availableDates = listOf("3", "10", "17", "24", "28", "29", "31")
                if (availableDates.contains(dates[i])) {
                    dateText.setTextColor(android.graphics.Color.BLUE)
                }
                
                dateText.setOnClickListener {
                    selectedDate = dates[i]
                    // Update selection visually
                    updateCalendarSelection()
                }
            } else {
                dateText.text = ""
                dateText.visibility = View.GONE
            }
            
            calendarGrid.addView(dateText)
        }
    }
    
    private fun updateCalendarSelection() {
        // Update visual selection if needed
        for (i in 0 until calendarGrid.childCount) {
            val child = calendarGrid.getChildAt(i) as? TextView
            child?.let {
                if (it.text.toString() == selectedDate) {
                    it.setBackgroundColor(android.graphics.Color.parseColor("#E3F2FD"))
                } else {
                    it.setBackgroundColor(android.graphics.Color.parseColor("#F0F0F0"))
                }
            }
        }
    }

    private fun setupTimeSlots() {
        // Setup time slots
        val layoutManager = LinearLayoutManager(requireContext())
        timeSlotsRecyclerView.layoutManager = layoutManager
        
        val timeSlots = generateTimeSlots()
        val timeSlotAdapter = TimeSlotAdapter(timeSlots) { timeSlot ->
            selectedTimeSlot = timeSlot
        }
        timeSlotsRecyclerView.adapter = timeSlotAdapter
    }

    private fun setupButtonListeners(view: View) {
        // Close button
        view.findViewById<ImageView>(R.id.btn_close).setOnClickListener {
            dismiss()
        }
        
        // Cancel button
        view.findViewById<TextView>(R.id.btn_cancel).setOnClickListener {
            dismiss()
        }
        
        // Confirm button
        view.findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            if (selectedDate.isNotEmpty() && selectedTimeSlot.isNotEmpty()) {
                onRescheduleConfirmed?.invoke(selectedDate, selectedTimeSlot)
                dismiss()
            }
        }
    }

    private fun generateCalendarDates(month: Int, year: Int): List<String> {
        val dates = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        
        // Add empty cells for days before month starts
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 0 until firstDayOfWeek) {
            dates.add("")
        }
        
        // Add days of the month
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..daysInMonth) {
            dates.add(day.toString())
        }
        
        // Add empty cells to fill the remaining grid (to make it 6 rows x 7 columns = 42 cells)
        val totalCells = 42
        while (dates.size < totalCells) {
            dates.add("")
        }
        
        return dates
    }

    private fun generateTimeSlots(): List<String> {
        return listOf(
            "7:00 am - 7:30 am",
            "7:30 am - 8:00 am",
            "8:00 am - 8:30 am",
            "8:30 am - 9:00 am",
            "9:00 am - 9:30 am",
            "9:30 am - 10:00 am",
            "10:00 am - 10:30 am",
            "10:30 am - 11:00 am",
            "11:00 am - 11:30 am",
            "11:30 am - 12:00 pm",
            "12:00 pm - 12:30 pm"
        )
    }

    private fun getMonthYearString(month: Int, year: Int): String {
        val months = arrayOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )
        return "${months[month]} $year"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.isDraggable = true
        dialog.behavior.isHideable = true
        return dialog
    }

    companion object {
        fun newInstance(onRescheduleConfirmed: (String, String) -> Unit): RescheduleBottomSheetDialog {
            val dialog = RescheduleBottomSheetDialog()
            dialog.onRescheduleConfirmed = onRescheduleConfirmed
            return dialog
        }
    }
}
