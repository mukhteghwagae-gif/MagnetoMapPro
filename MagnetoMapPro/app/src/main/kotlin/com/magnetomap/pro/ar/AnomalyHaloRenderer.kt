package com.magnetomap.pro.ar

import android.content.Context
import com.magnetomap.pro.domain.model.AnomalyDetection

class AnomalyHaloRenderer(private val context: Context) {
    fun init() {}
    fun updateAnomalies(anomalies: List<AnomalyDetection>) {}
    fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray) {}
}
