package com.jbarcelona.jobboardapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.jbarcelona.jobboardapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateJobFragment : AppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_job, container, false)
    }
}