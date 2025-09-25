package com.morbidelli.morbidelli_design

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CancelBookingBottomSheetDialog : BottomSheetDialogFragment() {

    private var bookingId: String = ""
    private var onCancelConfirmed: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_cancel_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set booking ID in title
        view.findViewById<TextView>(R.id.tv_cancel_title).text = "Cancel booking #$bookingId?"
        
        // Set up button listeners
        view.findViewById<TextView>(R.id.btn_keep_booking).setOnClickListener {
            dismiss()
        }
        
        view.findViewById<TextView>(R.id.btn_confirm_cancel).setOnClickListener {
            val reason = view.findViewById<EditText>(R.id.et_cancel_reason).text.toString()
            onCancelConfirmed?.invoke(reason)
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.behavior.isDraggable = true
        dialog.behavior.isHideable = true
        return dialog
    }

    companion object {
        fun newInstance(bookingId: String, onCancelConfirmed: (String) -> Unit): CancelBookingBottomSheetDialog {
            val dialog = CancelBookingBottomSheetDialog()
            dialog.bookingId = bookingId
            dialog.onCancelConfirmed = onCancelConfirmed
            return dialog
        }
    }
}
