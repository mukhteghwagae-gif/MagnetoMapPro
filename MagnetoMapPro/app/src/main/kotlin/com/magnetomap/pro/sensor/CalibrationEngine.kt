package com.magnetomap.pro.sensor

import com.magnetomap.pro.domain.model.FieldVector

class CalibrationEngine {
    private val samples = mutableListOf<FieldVector>()

    fun addSample(bx: Float, by: Float, bz: Float) {
        samples.add(FieldVector(bx, by, bz))
    }

    fun calibrate(): Pair<FloatArray, FloatArray> {
        if (samples.size < 10) {
            return Pair(
                floatArrayOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f),
                floatArrayOf(0f, 0f, 0f)
            )
        }

        var sumX = 0f; var sumY = 0f; var sumZ = 0f
        samples.forEach {
            sumX += it.x; sumY += it.y; sumZ += it.z
        }
        val n = samples.size.toFloat()
        val bias = floatArrayOf(sumX / n, sumY / n, sumZ / n)

        val softIron = floatArrayOf(1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f)

        return Pair(softIron, bias)
    }

    fun getConditionNumber(): Float {
        return 1.0f
    }

    fun clear() {
        samples.clear()
    }
}
