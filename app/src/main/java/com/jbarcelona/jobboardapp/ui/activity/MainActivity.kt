package com.jbarcelona.jobboardapp.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.databinding.ActivityMainBinding
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.network.model.Job
import com.jbarcelona.jobboardapp.ui.fragment.CreateJobFragment
import com.jbarcelona.jobboardapp.ui.fragment.FilterJobFragment
import com.jbarcelona.jobboardapp.ui.fragment.JobListingFragment
import com.jbarcelona.jobboardapp.ui.listener.OnRefreshJobListingListener
import com.jbarcelona.jobboardapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRefreshJobListingListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.search_jobs);
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            lifecycleOwner = this@MainActivity
        }
        loadJobListingFragment()
        setupObservers()
    }

    private fun loadJobListingFragment(data: ArrayList<Job>? = null, isFiltered: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_content, JobListingFragment.newInstance(data, isFiltered))
        transaction.commit()
    }

    private fun setupObservers() {
        viewModel.filterJobsEvent.observe(this) {
            showProgressDialog(false)
            when (it) {
                is NetworkResult.Success -> {
                    loadJobListingFragment(ArrayList(it.result), true)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    showErrorDialog(it.message)
                }
            }
        }
    }

    private fun showProgressDialog(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setNegativeButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_filter) {
            val dialog = FilterJobFragment()
            dialog.setListener(this)
            dialog.show(supportFragmentManager, "FilterJobFragment")
            return true
        }
        if (id == R.id.action_create) {
            val dialog = CreateJobFragment()
            dialog.setListener(this)
            dialog.show(supportFragmentManager, "CreateJobFragment")
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onUpdateJobListing() {
        loadJobListingFragment()
    }

    override fun onShowFilteredJobs(keyword: String, industry: Int) {
        showProgressDialog(true)
        viewModel.filterJobs(keyword, industry)
    }
}