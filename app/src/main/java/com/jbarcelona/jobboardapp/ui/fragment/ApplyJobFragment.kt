package com.jbarcelona.jobboardapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jbarcelona.jobboardapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyJobFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_job, container, false)
    }
}