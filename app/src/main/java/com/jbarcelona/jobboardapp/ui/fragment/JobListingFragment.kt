package com.jbarcelona.jobboardapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.databinding.FragmentJobListingBinding
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.ui.adapter.JobAdapter
import com.jbarcelona.jobboardapp.ui.viewmodel.JobListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobListingFragment : BaseFragment(), JobAdapter.OnJobClickListener {

    private lateinit var viewModel: JobListingViewModel
    private lateinit var binding: FragmentJobListingBinding
    private lateinit var jobLayoutManager: RecyclerView.LayoutManager
    private lateinit var jobAdapter: JobAdapter

    companion object {
        private const val REQUEST_APPLY_JOB = 1000
        private const val REQUEST_CREATE_JOB = 1001
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(JobListingViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentJobListingBinding>(
            inflater,
            R.layout.fragment_job_listing,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        initAdapter()
        viewModel.getAllJobs()
        setupObservers()
        return binding.root
    }

    private fun initAdapter() {
        jobLayoutManager = LinearLayoutManager(requireContext())
        jobAdapter = JobAdapter(emptyList(), this)
        binding.rvJobs.layoutManager = jobLayoutManager
        binding.rvJobs.adapter = jobAdapter
    }

    private fun setupObservers() {
        viewModel.populateJobListEvent.observe(viewLifecycleOwner) {
            when (it) {
                is JobListingViewModel.JobResult.Success -> {
                    if (!it.responseData.isNullOrEmpty()) {
                        binding.llContent.visibility = View.VISIBLE
                        binding.tvMessage.visibility = View.GONE
                        jobAdapter.jobList = it.responseData
                        jobAdapter.notifyDataSetChanged()
                    } else {
                        binding.llContent.visibility = View.GONE
                        binding.tvMessage.visibility = View.VISIBLE
                    }
                }
                is JobListingViewModel.JobResult.Error -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        viewModel.deleteJobEvent.observe(viewLifecycleOwner) {

        }
    }

    override fun onApplyJob(job: Job) {
        fragmentManager?.let { fm ->
            ApplyJobFragment.newInstance().let {
                it.setTargetFragment(this, REQUEST_APPLY_JOB)
                it.show(fm, ApplyJobFragment::class.java.simpleName)
            }
        }
    }

    override fun onUpdateJob(job: Job) {
        TODO("Not yet implemented")
    }

    override fun onDeleteJob(job: Job) {
        viewModel.deleteJob(job)
    }
}