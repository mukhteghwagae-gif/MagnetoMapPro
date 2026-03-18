package com.magnetomap.pro.domain.model

data class AnomalyDetection(
    val id: String,
    val x: Float,
    val y: Float,
    val z: Float,
    val peakMagnitude: Float,
    val classification: Int,
    val confidence: Float,
    val depthEstimate: Float
)
