package com.morbidelli.morbidelli_design.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.morbidelli.morbidelli_design.R
import com.morbidelli.morbidelli_design.model.ContactInfo
import com.morbidelli.morbidelli_design.model.LocationModel
import com.morbidelli.morbidelli_design.model.WorkingHours

class DealerDetailsBottomSheet : BottomSheetDialogFragment() {

    private var location: LocationModel? = null
    private var onLocationSelected: ((LocationModel) -> Unit)? = null

    companion object {
        private const val ARG_LOCATION = "arg_location"

        fun newInstance(location: LocationModel): DealerDetailsBottomSheet {
            val fragment = DealerDetailsBottomSheet()
            val args = Bundle()
            args.putSerializable(ARG_LOCATION, location)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            location = it.getSerializable(ARG_LOCATION) as? LocationModel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dealer_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initViews(view)
        populateData()
        setupClickListeners(view)
    }

    private fun initViews(view: View) {
        // Views are already defined in the layout
    }

    private fun populateData() {
        location?.let { location ->
            view?.let { view ->
                view.findViewById<TextView>(R.id.tv_dealership_name)?.text = location.dealershipName
                view.findViewById<TextView>(R.id.tv_address)?.text = location.address
                view.findViewById<TextView>(R.id.tv_available_motorbikes)?.text = "Available motorbike: ${location.availableMotorbikes}"
                view.findViewById<TextView>(R.id.tv_from_date)?.text = "From: ${location.fromDate}"

                // Populate working hours
                location.workingHours?.let { hours ->
                    view.findViewById<TextView>(R.id.tv_weekdays_hours)?.text = hours.weekdays
                    view.findViewById<TextView>(R.id.tv_weekend_hours)?.text = hours.weekend
                }

                // Populate contact info
                location.contactInfo?.let { contact ->
                    view.findViewById<TextView>(R.id.tv_phone_number)?.text = contact.phoneNumber
                    view.findViewById<TextView>(R.id.tv_email)?.text = contact.email
                }
            }
        }
    }

    private fun setupClickListeners(view: View) {
        // Close button
        view.findViewById<ImageView>(R.id.btn_close)?.setOnClickListener {
            dismiss()
        }

        // External link
        view.findViewById<ImageView>(R.id.iv_external_link)?.setOnClickListener {
            // Handle external link click
        }

        // Select location button
        view.findViewById<Button>(R.id.btn_select_location)?.setOnClickListener {
            location?.let { location ->
                onLocationSelected?.invoke(location)
                dismiss()
            }
        }
    }

    fun setOnLocationSelectedListener(listener: (LocationModel) -> Unit) {
        onLocationSelected = listener
    }
}
