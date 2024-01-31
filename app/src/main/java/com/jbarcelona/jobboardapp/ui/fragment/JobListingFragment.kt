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
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.ui.adapter.JobAdapter
import com.jbarcelona.jobboardapp.ui.listener.OnRefreshJobListingListener
import com.jbarcelona.jobboardapp.ui.viewmodel.JobListingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JobListingFragment : BaseFragment(), JobAdapter.OnJobClickListener, OnRefreshJobListingListener {

    private lateinit var viewModel: JobListingViewModel
    private lateinit var binding: FragmentJobListingBinding
    private lateinit var jobLayoutManager: RecyclerView.LayoutManager
    private lateinit var jobAdapter: JobAdapter

    companion object {
        private const val ARG_FILTERED_JOB_LIST = "ARG_FILTERED_JOB_LIST"
        private const val ARG_IS_FILTERED = "ARG_IS_FILTERED"
        private const val REQUEST_APPLY_JOB = 1000
        private const val REQUEST_UPDATE_JOB = 1001

        @JvmStatic
        fun newInstance(filteredJobList: ArrayList<Job>? = null, isFiltered: Boolean = false) =
            JobListingFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_FILTERED_JOB_LIST, filteredJobList)
                    putBoolean(ARG_IS_FILTERED, isFiltered)
                }
            }
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
        val isFiltered = arguments?.getBoolean(ARG_IS_FILTERED) ?: false
        if (isFiltered) {
            val filteredJobList: ArrayList<Job>? = arguments?.getParcelableArrayList(ARG_FILTERED_JOB_LIST)
            populateJobList(filteredJobList)
        } else {
            setProgressBarVisibility(true)
            viewModel.getAllJobs()
        }
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
            setProgressBarVisibility(false)
            when (it) {
                is NetworkResult.Success -> {
                    populateJobList(it.result)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    showErrorDialog(it.message)
                }
            }
        }
        viewModel.deleteJobEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(activity, "Job deleted successfully.", Toast.LENGTH_LONG).show()
                    jobAdapter.jobList = emptyList()
                    viewModel.getAllJobs()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    showErrorDialog(it.message)
                }
            }
        }
    }

    private fun populateJobList(result: List<Job>?) {
        if (!result.isNullOrEmpty()) {
            binding.llContent.visibility = View.VISIBLE
            binding.tvMessage.visibility = View.GONE
            jobAdapter.jobList = result
            jobAdapter.notifyDataSetChanged()
        } else {
            binding.llContent.visibility = View.GONE
            binding.tvMessage.visibility = View.VISIBLE
        }
    }

    override fun onApplyJob(job: Job) {
        fragmentManager?.let { fm ->
            ApplyJobFragment.newInstance(job.id).let {
                it.setTargetFragment(this, REQUEST_APPLY_JOB)
                it.show(fm, ApplyJobFragment::class.java.simpleName)
            }
        }
    }

    override fun onUpdateJob(job: Job) {
        fragmentManager?.let { fm ->
            UpdateJobFragment.newInstance(job).let {
                it.setTargetFragment(this, REQUEST_UPDATE_JOB)
                it.setListener(this)
                it.show(fm, UpdateJobFragment::class.java.simpleName)
            }
        }
    }

    private fun setProgressBarVisibility(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }

    override fun onDeleteJob(job: Job) {
        setProgressBarVisibility(true)
        viewModel.deleteJob(job)
    }

    override fun onUpdateJobListing() {
        setProgressBarVisibility(true)
        viewModel.getAllJobs()
    }

    override fun onShowFilteredJobs(keyword: String, industry: Int) {}
}