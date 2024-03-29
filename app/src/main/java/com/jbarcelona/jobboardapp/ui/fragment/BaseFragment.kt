package com.jbarcelona.jobboardapp.ui.fragment

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

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