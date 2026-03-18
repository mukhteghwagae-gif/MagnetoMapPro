package com.magnetomap.pro.sensor

import kotlin.math.sqrt

class SensorFusionFilter {
    private var q0 = 1.0f
    private var q1 = 0.0f
    private var q2 = 0.0f
    private var q3 = 0.0f
    private val beta = 0.1f

    fun update(gx: Float, gy: Float, gz: Float, ax: Float, ay: Float, az: Float, dt: Float) {
        var recipNorm: Float
        var s0: Float; var s1: Float; var s2: Float; var s3: Float
        var qDot1: Float; var qDot2: Float; var qDot3: Float; var qDot4: Float
        var _2q0: Float; var _2q1: Float; var _2q2: Float; var _2q3: Float
        var _4q0: Float; var _4q1: Float; var _4q2: Float; var _8q1: Float; var _8q2: Float
        var q0q0: Float; var q1q1: Float; var q2q2: Float; var q3q3: Float

        qDot1 = 0.5f * (-q1 * gx - q2 * gy - q3 * gz)
        qDot2 = 0.5f * (q0 * gx + q2 * gz - q3 * gy)
        qDot3 = 0.5f * (q0 * gy - q1 * gz + q3 * gx)
        qDot4 = 0.5f * (q0 * gz + q1 * gy - q2 * gx)

        if (!((ax == 0.0f) && (ay == 0.0f) && (az == 0.0f))) {
            var nax = ax; var nay = ay; var naz = az
            recipNorm = (1.0f / sqrt(nax * nax + nay * nay + naz * naz))
            nax *= recipNorm; nay *= recipNorm; naz *= recipNorm

            _2q0 = 2.0f * q0; _2q1 = 2.0f * q1; _2q2 = 2.0f * q2; _2q3 = 2.0f * q3
            _4q0 = 4.0f * q0; _4q1 = 4.0f * q1; _4q2 = 4.0f * q2; _8q1 = 8.0f * q1; _8q2 = 8.0f * q2
            q0q0 = q0 * q0; q1q1 = q1 * q1; q2q2 = q2 * q2; q3q3 = q3 * q3

            s0 = _4q0 * q2q2 + _2q2 * nax + _4q0 * q1q1 - _2q1 * nay
            s1 = _4q1 * q3q3 - _2q3 * nax + 4.0f * q0q0 * q1 - _2q0 * nay - _4q1 + _8q1 * q1q1 + _8q1 * q2q2 + _4q1 * naz
            s2 = 4.0f * q0q0 * q2 + _2q0 * nax + _4q2 * q3q3 - _2q3 * nay - _4q2 + _8q2 * q1q1 + _8q2 * q2q2 + _4q2 * naz
            s3 = 4.0f * q1q1 * q3 - _2q1 * nax + 4.0f * q2q2 * q3 - _2q2 * nay

            recipNorm = (1.0f / sqrt(s0 * s0 + s1 * s1 + s2 * s2 + s3 * s3))
            s0 *= recipNorm; s1 *= recipNorm; s2 *= recipNorm; s3 *= recipNorm

            qDot1 -= beta * s0
            qDot2 -= beta * s1
            qDot3 -= beta * s2
            qDot4 -= beta * s3
        }

        q0 += qDot1 * dt; q1 += qDot2 * dt; q2 += qDot3 * dt; q3 += qDot4 * dt

        recipNorm = (1.0f / sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3))
        q0 *= recipNorm; q1 *= recipNorm; q2 *= recipNorm; q3 *= recipNorm
    }

    fun rotateVector(vx: Float, vy: Float, vz: Float): FloatArray {
        val q0q0 = q0 * q0; val q1q1 = q1 * q1; val q2q2 = q2 * q2; val q3q3 = q3 * q3
        val q0q1 = q0 * q1; val q0q2 = q0 * q2; val q0q3 = q0 * q3
        val q1q2 = q1 * q2; val q1q3 = q1 * q3; val q2q3 = q2 * q3

        val rx = vx * (q0q0 + q1q1 - q2q2 - q3q3) + vy * 2.0f * (q1q2 - q0q3) + vz * 2.0f * (q1q3 + q0q2)
        val ry = vx * 2.0f * (q1q2 + q0q3) + vy * (q0q0 - q1q1 + q2q2 - q3q3) + vz * 2.0f * (q2q3 - q0q1)
        val rz = vx * 2.0f * (q1q3 - q0q2) + vy * 2.0f * (q2q3 + q0q1) + vz * (q0q0 - q1q1 - q2q2 + q3q3)
        
        return floatArrayOf(rx, ry, rz)
    }
}
