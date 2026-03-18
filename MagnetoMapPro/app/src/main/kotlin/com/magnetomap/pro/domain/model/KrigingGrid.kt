package com.magnetomap.pro.domain.model

data class KrigingGrid(
    val resolution: Float,
    val minX: Float,
    val maxX: Float,
    val minY: Float,
    val maxY: Float,
    val minZ: Float,
    val maxZ: Float,
    val values: FloatArray,
    val variances: FloatArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as KrigingGrid
        return resolution == other.resolution && values.contentEquals(other.values)
    }
    override fun hashCode(): Int {
        var result = resolution.hashCode()
        result = 31 * result + values.contentHashCode()
        return result
    }
}
