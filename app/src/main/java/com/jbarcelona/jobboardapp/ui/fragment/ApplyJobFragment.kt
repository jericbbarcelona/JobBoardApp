package com.jbarcelona.jobboardapp.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.jbarcelona.jobboardapp.R
import com.jbarcelona.jobboardapp.databinding.FragmentApplyJobBinding
import com.jbarcelona.jobboardapp.ui.viewmodel.ApplyJobViewModel
import com.jbarcelona.jobboardapp.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplyJobFragment : AppCompatDialogFragment() {

    private lateinit var viewModel: ApplyJobViewModel
    private lateinit var binding: FragmentApplyJobBinding

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
        viewModel = ViewModelProvider(this).get(ApplyJobViewModel::class.java)
        binding = DataBindingUtil.inflate<FragmentApplyJobBinding>(
            inflater,
            R.layout.fragment_apply_job,
            container,
            false
        ).apply {
            this.lifecycleOwner = activity
        }
        setupListeners()
        setupObservers()
        return binding.root
    }

    private fun setupListeners() {
        binding.apply {
            hideTextAfterTextChanged(etEmailAddress, tvErrorEmailAddress)
            hideTextAfterTextChanged(etFirstName, tvErrorFirstName)
            hideTextAfterTextChanged(etLastName, tvErrorLastName)
            btnApply.setOnClickListener {
                if (validateFields()) {

                }
            }
            ibExit.setOnClickListener {
                dismiss()
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
        return isValidEmail() and isValidFirstName() and isValidLastName()
    }

    private fun isValidFirstName(): Boolean {
        val firstName = binding.etFirstName.text.toString().trim()
        if (firstName.isEmpty()) {
            binding.tvErrorFirstName.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun isValidLastName(): Boolean {
        val lastName = binding.etLastName.text.toString().trim()
        if (lastName.isEmpty()) {
            binding.tvErrorLastName.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun isValidEmail(): Boolean {
        val email = binding.etEmailAddress.text.toString().trim()
        if (!Util.isValidEmail(email)) {
            binding.tvErrorEmailAddress.visibility = View.VISIBLE
            return false
        }
        return true
    }

    private fun setupObservers() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = ApplyJobFragment()
    }
}