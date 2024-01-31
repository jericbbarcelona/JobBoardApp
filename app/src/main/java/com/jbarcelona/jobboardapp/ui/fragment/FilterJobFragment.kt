package com.jbarcelona.jobboardapp.ui.fragment

import android.app.Activity
import android.content.Intent
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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.databinding.FragmentFilterJobBinding
import com.jbarcelona.jobboardapp.ui.listener.OnRefreshJobListingListener
import com.jbarcelona.jobboardapp.ui.viewmodel.FilterJobViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterJobFragment : BaseDialogFragment() {

    private lateinit var viewModel: FilterJobViewModel
    private lateinit var binding: FragmentFilterJobBinding
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
        viewModel = ViewModelProvider(this).get(FilterJobViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentFilterJobBinding>(
            inflater,
            R.layout.fragment_filter_job,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        setupIndustrySelection()
        setupListeners()
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
            hideTextAfterTextChanged(etKeyword, tvErrorKeyword)
            btnApply.setOnClickListener {
                if (validateFields()) {
                    val keyword = etKeyword.text.toString().trim()
                    val position = spIndustry.selectedItemPosition
                    listener?.onShowFilteredJobs(keyword, position)
                    dismiss()
                }
            }
            ibExit.setOnClickListener {
                dismiss()
            }
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
        return isValidKeyword() and validateSelection()
    }

    private fun isValidKeyword(): Boolean {
        val keyword = binding.etKeyword.text.toString().trim()
        if (keyword.isEmpty()) {
            binding.tvErrorKeyword.visibility = View.VISIBLE
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

    companion object {
        const val EXTRA_FILTER_KEYWORD = "EXTRA_FILTER_KEYWORD"
        const val EXTRA_FILTER_INDUSTRY = "EXTRA_FILTER_INDUSTRY"
        @JvmStatic
        fun newInstance() = FilterJobFragment()
    }
}