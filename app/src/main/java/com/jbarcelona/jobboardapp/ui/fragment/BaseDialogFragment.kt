package com.jbarcelona.jobboardapp.ui.fragment

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

abstract class BaseDialogFragment : AppCompatDialogFragment() {

    fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setNegativeButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}