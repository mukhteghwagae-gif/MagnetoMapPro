package com.magnetomap.pro.domain.model

import kotlin.math.sqrt

data class FieldVector(val x: Float, val y: Float, val z: Float) {
    val magnitude: Float get() = sqrt(x * x + y * y + z * z)
    
    operator fun plus(other: FieldVector) = FieldVector(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: FieldVector) = FieldVector(x - other.x, y - other.y, z - other.z)
    operator fun times(scalar: Float) = FieldVector(x * scalar, y * scalar, z * scalar)
}
