package com.magnetomap.pro.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.magnetomap.pro.databinding.FragmentSurveyHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SurveyHistoryFragment : Fragment() {
    private var _binding: FragmentSurveyHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SurveyHistoryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSurveyHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
