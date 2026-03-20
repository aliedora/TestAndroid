package com.example.testandroid.ui.preferences

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testandroid.databinding.FragmentPreferencesBinding
import com.example.testandroid.util.PreferencesHelper

class PreferencesFragment : Fragment() {

    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!

    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesHelper = PreferencesHelper(requireContext())
        refreshSavedValue()

        binding.btnSave.setOnClickListener {
            val value = binding.etPreferenceValue.text?.toString() ?: ""
            preferencesHelper.saveValue(value)
            refreshSavedValue()
        }

        binding.btnClear.setOnClickListener {
            preferencesHelper.clear()
            binding.etPreferenceValue.text?.clear()
            refreshSavedValue()
        }
    }

    private fun refreshSavedValue() {
        val saved = preferencesHelper.getValue()
        binding.tvSavedValue.text = saved ?: getString(com.example.testandroid.R.string.tv_saved_value_default)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
