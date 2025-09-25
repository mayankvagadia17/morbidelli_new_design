package com.morbidelli.morbidelli_design.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.Booking
import com.morbidelli.morbidelli_design.model.BookingStatus

class BookingAdapter(
    private var bookings: List<Booking>,
    private val onRescheduleClick: (Booking) -> Unit,
    private val onCancelClick: (Booking) -> Unit,
    private val onItemClick: (Booking) -> Unit
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    private var currentlyOpenMenuPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.bind(booking, position)
    }

    override fun getItemCount(): Int = bookings.size

    fun updateBookings(newBookings: List<Booking>) {
        bookings = newBookings
        notifyDataSetChanged()
    }

    inner class BookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)
        private val ivMenu: ImageView = itemView.findViewById(R.id.iv_menu)
        private val llPopupMenu: LinearLayout = itemView.findViewById(R.id.ll_popup_menu)
        private val tvReschedule: LinearLayout = itemView.findViewById(R.id.tv_reschedule)
        private val tvCancel: LinearLayout = itemView.findViewById(R.id.tv_cancel)
        private val ivVehicle: ImageView = itemView.findViewById(R.id.iv_vehicle)
        private val tvVehicleModel: TextView = itemView.findViewById(R.id.tv_vehicle_model)
        private val tvVehicleCode: TextView = itemView.findViewById(R.id.tv_vehicle_code)
        private val vCompanyLogo: View = itemView.findViewById(R.id.v_company_logo)
        private val tvCompanyInitial: TextView = itemView.findViewById(R.id.tv_company_initial)
        private val tvCompanyName: TextView = itemView.findViewById(R.id.tv_company_name)
        private val tvCompanyAddress: TextView = itemView.findViewById(R.id.tv_company_address)

        fun bind(booking: Booking, position: Int) {
            tvDateTime.text = "${booking.date}   ${booking.time}"
            tvVehicleModel.text =
                "${booking.vehicleModel} - ${itemView.context.getString(R.string.naked)}"
            tvVehicleCode.text =
                itemView.context.getString(R.string.vehicle_code_format, booking.vehicleCode)
            tvCompanyName.text = booking.companyName
            tvCompanyAddress.text = booking.address

            val companyColors = getCompanyColors(booking.companyName)
            vCompanyLogo.setBackgroundColor(Color.parseColor(companyColors.first))
            tvCompanyInitial.text = companyColors.second

            setupMenuHandling(booking, position)
            
            // Show/hide menu options based on booking status
            tvReschedule.visibility =
                if (booking.hasRescheduleOption && booking.status == BookingStatus.UPCOMING)
                    View.VISIBLE else View.GONE
            tvCancel.visibility =
                if (booking.hasCancelOption && booking.status == BookingStatus.UPCOMING)
                    View.VISIBLE else View.GONE

            // Show/hide popup menu based on current state
            llPopupMenu.visibility =
                if (currentlyOpenMenuPosition == position) View.VISIBLE else View.GONE
        }

        private fun setupMenuHandling(booking: Booking, position: Int) {
            ivMenu.setOnClickListener {
                if (currentlyOpenMenuPosition == position) {
                    closeAllMenus()
                } else {
                    closeAllMenus()
                    currentlyOpenMenuPosition = position
                    llPopupMenu.visibility = View.VISIBLE
                }
            }

            tvReschedule.setOnClickListener {
                closeAllMenus()
                onRescheduleClick(booking)
            }

            tvCancel.setOnClickListener {
                closeAllMenus()
                onCancelClick(booking)
            }

            itemView.setOnClickListener {
                if (currentlyOpenMenuPosition == position) {
                    closeAllMenus()
                } else {
                    onItemClick(booking)
                }
            }
        }

        private fun getCompanyColors(companyName: String): Pair<String, String> {
            return when (companyName.lowercase()) {
                "oakley motorcycles" -> {
                    val colors = listOf(
                        Pair("#D32F2F", "O"),
                        Pair("#4CAF50", "O"),
                        Pair("#424242", "O")
                    )
                    colors[adapterPosition % colors.size]
                }

                else -> Pair("#666666", companyName.first().toString().uppercase())
            }
        }
    }

    private fun closeAllMenus() {
        if (currentlyOpenMenuPosition != -1) {
            val previousPosition = currentlyOpenMenuPosition
            currentlyOpenMenuPosition = -1
            notifyItemChanged(previousPosition)
        }
    }

    fun closeMenus() {
        closeAllMenus()
    }
}
