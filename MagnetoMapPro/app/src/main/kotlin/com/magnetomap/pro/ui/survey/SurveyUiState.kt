package com.magnetomap.pro.ui.survey

import com.magnetomap.pro.domain.model.AnomalyDetection
import com.magnetomap.pro.domain.model.MagneticSample

data class SurveyUiState(
    val isRecording: Boolean = false,
    val sampleCount: Int = 0,
    val currentMagnitude: Float = 0f,
    val coveragePercentage: Float = 0f,
    val krigingScore: Float = 1.0f,
    val anomalies: List<AnomalyDetection> = emptyList(),
    val latestSample: MagneticSample? = null
)
