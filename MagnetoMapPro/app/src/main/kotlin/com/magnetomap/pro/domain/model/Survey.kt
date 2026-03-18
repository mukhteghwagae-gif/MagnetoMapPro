package com.magnetomap.pro.domain.model

data class Survey(
    val id: Long = 0,
    val name: String,
    val startTime: Long,
    val endTime: Long? = null,
    val sampleCount: Int = 0,
    val minX: Float = 0f,
    val maxX: Float = 0f,
    val minY: Float = 0f,
    val maxY: Float = 0f,
    val minZ: Float = 0f,
    val maxZ: Float = 0f
)
