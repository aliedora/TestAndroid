package com.example.testandroid.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testandroid.R
import com.example.testandroid.data.network.RetrofitClient
import com.example.testandroid.data.repository.ApiRepositoryImpl
import com.example.testandroid.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(ApiRepositoryImpl(RetrofitClient.apiService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShowToast.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.toast_message), Toast.LENGTH_SHORT).show()
        }

        binding.btnShowDialog.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
                .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                .show()
        }

        binding.btnOpenWebView.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_webview)
        }

        binding.btnGoToDetails.setOnClickListener {
            val args = bundleOf("itemData" to "Hello from HomeFragment")
            findNavController().navigate(R.id.action_home_to_detail, args)
        }

        binding.btnCallApi.setOnClickListener {
            viewModel.fetchPost()
        }

        binding.btnPreferences.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_preferences)
        }

        viewModel.apiResult.observe(viewLifecycleOwner) { result ->
            binding.tvApiResult.text = when (result) {
                is ApiResult.Loading -> getString(R.string.loading)
                is ApiResult.Success -> "${result.title}\n\n${result.body}"
                is ApiResult.Error -> "Error: ${result.message}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
