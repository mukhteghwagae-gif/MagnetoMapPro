package com.magnetomap.pro.kriging

import kotlin.math.pow

class Variogram(
    var nugget: Float = 10.0f,
    var sill: Float = 500.0f,
    var range: Float = 2.0f
) {
    fun compute(distance: Float): Float {
        if (distance == 0.0f) return 0.0f
        val c = sill - nugget
        return if (distance <= range) {
            val ratio = distance / range
            nugget + c * (1.5f * ratio - 0.5f * ratio.pow(3))
        } else {
            nugget + c
        }
    }
}
