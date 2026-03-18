package com.magnetomap.pro.domain.model

data class MagneticSample(
    val x: Float,
    val y: Float,
    val z: Float,
    val bx: Float,
    val by: Float,
    val bz: Float,
    val magnitude: Float,
    val timestamp: Long,
    val isContaminated: Boolean = false
)
