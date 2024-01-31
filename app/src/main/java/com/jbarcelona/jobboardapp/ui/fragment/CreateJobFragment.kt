package com.jbarcelona.jobboardapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.databinding.FragmentCreateJobBinding
import com.jbarcelona.jobboardapp.network.NetworkResult
import com.jbarcelona.jobboardapp.ui.listener.OnRefreshJobListingListener
import com.jbarcelona.jobboardapp.ui.viewmodel.CreateJobViewModel
import com.jbarcelona.jobboardapp.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateJobFragment : BaseDialogFragment() {

    private lateinit var viewModel: CreateJobViewModel
    private lateinit var binding: FragmentCreateJobBinding
    private var listener: OnRefreshJobListingListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = STYLE_NO_FRAME
        setStyle(style, R.style.CustomNoTitleDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            val params: ViewGroup.LayoutParams = it.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.MATCH_PARENT
            it.attributes = params as WindowManager.LayoutParams
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(CreateJobViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentCreateJobBinding>(
            inflater,
            R.layout.fragment_create_job,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        setupIndustrySelection()
        setupListeners()
        setupObservers()
        return binding.root
    }

    fun setListener(listener: OnRefreshJobListingListener) {
        this.listener = listener
    }

    private fun setupIndustrySelection() {
        val industryList = resources.getStringArray(R.array.industry)
        val adapter = ArrayAdapter(requireContext(), R.layout.sp_text_normal, industryList)
        adapter.setDropDownViewResource(R.layout.sp_text_large)
        binding.spIndustry.adapter = adapter
    }

    private fun setupListeners() {
        binding.apply {
            hideTextAfterTextChanged(etTitle, tvErrorTitle)
            hideTextAfterTextChanged(etDescription, tvErrorDescription)
            hideTextAfterTextChanged(etNoOfOpenings, tvErrorNoOfOpenings)
            btnApply.setOnClickListener {
                if (validateFields()) {
                    triggerCreateJob()
                }
            }
            ibExit.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun triggerCreateJob() {
        setProgressBarVisibility(true)
        val noOfOpenings = binding.etNoOfOpenings.text.toString().trim()
        val title = binding.etTitle.text.toString().trim()
        val description = binding.etDescription.text.toString()
        val position = binding.spIndustry.selectedItemPosition
        viewModel.createJob(noOfOpenings, title, description, position)
    }

    private fun setProgressBarVisibility(visible: Boolean) {
        if (visible) {
            binding.rlProgressbar.visibility = View.VISIBLE
        } else {
            binding.rlProgressbar.visibility = View.GONE
        }
    }

    private fun hideTextAfterTextChanged(editText: EditText, tvError: TextView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tvError.visibility = View.GONE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun validateFields(): Boolean {
        return isValidNoOfOpenings() and isValidTitle() and isValidDescription() and validateSelection()
    }

    private fun isValidNoOfOpenings(): Boolean {
        val noOfOpenings = binding.etNoOfOpenings.text.toString().trim()
        if (noOfOpenings.isEmpty() && !Util.isInteger(noOfOpenings)) {
            binding.tvErrorNoOfOpenings.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun isValidTitle(): Boolean {
        val title = binding.etTitle.text.toString().trim()
        if (title.isEmpty()) {
            binding.tvErrorTitle.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun validateSelection(): Boolean {
        val validation = binding.spIndustry.selectedItemPosition != 0
        if (!validation) {
            binding.tvErrorIndustry.visibility = View.VISIBLE
        }
        return validation
    }

    private fun isValidDescription(): Boolean {
        val description = binding.etDescription.text.toString().trim()
        if (description.isEmpty()) {
            binding.tvErrorDescription.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun setupObservers() {
        viewModel.createJobEvent.observe(viewLifecycleOwner) {
            setProgressBarVisibility(false)
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(activity, "Job created successfully.", Toast.LENGTH_LONG).show()
                    listener?.onUpdateJobListing()
                    dismiss()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                    showErrorDialog(it.message)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateJobFragment()
    }
}