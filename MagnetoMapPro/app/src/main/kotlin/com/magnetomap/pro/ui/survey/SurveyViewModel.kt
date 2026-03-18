package com.magnetomap.pro.ui.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magnetomap.pro.domain.model.MagneticSample
import com.magnetomap.pro.domain.usecase.StartSurveyUseCase
import com.magnetomap.pro.domain.usecase.StopSurveyUseCase
import com.magnetomap.pro.sensor.MagnetometerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val magnetometerManager: MagnetometerManager,
    private val startSurveyUseCase: StartSurveyUseCase,
    private val stopSurveyUseCase: StopSurveyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SurveyUiState())
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private var currentSurveyId: Long? = null
    private val recordedSamples = mutableListOf<MagneticSample>()

    init {
        magnetometerManager.start()
        viewModelScope.launch {
            magnetometerManager.magneticFlow.collect { sample ->
                if (sample != null) {
                    _uiState.update { it.copy(currentMagnitude = sample.magnitude, latestSample = sample) }
                    if (_uiState.value.isRecording) {
                        recordedSamples.add(sample)
                        _uiState.update { it.copy(sampleCount = recordedSamples.size) }
                    }
                }
            }
        }
    }

    fun toggleRecording() {
        val isRecording = _uiState.value.isRecording
        if (isRecording) {
            viewModelScope.launch {
                currentSurveyId?.let { stopSurveyUseCase(it, recordedSamples) }
                _uiState.update { it.copy(isRecording = false) }
                recordedSamples.clear()
                currentSurveyId = null
            }
        } else {
            viewModelScope.launch {
                currentSurveyId = startSurveyUseCase("Survey ${System.currentTimeMillis()}")
                _uiState.update { it.copy(isRecording = true, sampleCount = 0) }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        magnetometerManager.stop()
    }
}
