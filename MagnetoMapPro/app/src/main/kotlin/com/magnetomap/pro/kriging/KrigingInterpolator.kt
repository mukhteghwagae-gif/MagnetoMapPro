package com.magnetomap.pro.kriging

import com.magnetomap.pro.domain.model.MagneticSample
import kotlin.math.sqrt

class KrigingInterpolator(private val variogram: Variogram) {

    fun interpolate(samples: List<MagneticSample>, targetX: Float, targetY: Float, targetZ: Float): Pair<Float, Float> {
        val n = samples.size
        if (n == 0) return Pair(0f, 0f)
        if (n == 1) return Pair(samples[0].magnitude, variogram.nugget)

        val a = Array(n + 1) { FloatArray(n + 1) }
        val b = FloatArray(n + 1)

        for (i in 0 until n) {
            for (j in 0 until n) {
                val dist = distance(samples[i], samples[j])
                a[i][j] = variogram.compute(dist)
            }
            a[i][n] = 1.0f
            a[n][i] = 1.0f
            
            val distToTarget = distance(samples[i], targetX, targetY, targetZ)
            b[i] = variogram.compute(distToTarget)
        }
        a[n][n] = 0.0f
        b[n] = 1.0f

        val w = solveLU(a, b, n + 1) ?: return Pair(0f, 0f)

        var estimate = 0.0f
        var variance = variogram.sill
        for (i in 0 until n) {
            estimate += w[i] * samples[i].magnitude
            variance -= w[i] * b[i]
        }
        variance -= w[n]

        return Pair(estimate, variance.coerceAtLeast(0f))
    }

    private fun distance(s1: MagneticSample, s2: MagneticSample): Float {
        val dx = s1.x - s2.x
        val dy = s1.y - s2.y
        val dz = s1.z - s2.z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    private fun distance(s: MagneticSample, tx: Float, ty: Float, tz: Float): Float {
        val dx = s.x - tx
        val dy = s.y - ty
        val dz = s.z - tz
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    private fun solveLU(A: Array<FloatArray>, b: FloatArray, n: Int): FloatArray? {
        val L = Array(n) { FloatArray(n) }
        val U = Array(n) { FloatArray(n) }

        for (i in 0 until n) {
            for (k in i until n) {
                var sum = 0f
                for (j in 0 until i) sum += (L[i][j] * U[j][k])
                U[i][k] = A[i][k] - sum
            }
            for (k in i until n) {
                if (i == k) {
                    L[i][i] = 1f
                } else {
                    var sum = 0f
                    for (j in 0 until i) sum += (L[k][j] * U[j][i])
                    if (U[i][i] == 0f) U[i][i] = 1e-6f
                    L[k][i] = (A[k][i] - sum) / U[i][i]
                }
            }
        }

        val y = FloatArray(n)
        for (i in 0 until n) {
            var sum = 0f
            for (j in 0 until i) sum += L[i][j] * y[j]
            y[i] = b[i] - sum
        }

        val x = FloatArray(n)
        for (i in n - 1 downTo 0) {
            var sum = 0f
            for (j in i + 1 until n) sum += U[i][j] * x[j]
            x[i] = (y[i] - sum) / U[i][i]
        }
        return x
    }
}
