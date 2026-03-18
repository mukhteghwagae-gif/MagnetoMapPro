package com.magnetomap.pro.ui.history

import androidx.lifecycle.ViewModel
import com.magnetomap.pro.data.repository.SurveyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SurveyHistoryViewModel @Inject constructor(
    private val repository: SurveyRepository
) : ViewModel() {
    val surveys = repository.getAllSurveys()
}
