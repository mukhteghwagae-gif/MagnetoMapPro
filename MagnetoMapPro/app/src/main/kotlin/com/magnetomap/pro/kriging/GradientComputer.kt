package com.magnetomap.pro.kriging

import com.magnetomap.pro.domain.model.KrigingGrid
import kotlin.math.sqrt

class GradientComputer {
    fun computeGradient(grid: KrigingGrid, x: Float, y: Float, z: Float): FloatArray {
        val res = grid.resolution
        val vX1 = getValue(grid, x + res, y, z) ?: getValue(grid, x, y, z) ?: 0f
        val vX0 = getValue(grid, x - res, y, z) ?: getValue(grid, x, y, z) ?: 0f
        val vY1 = getValue(grid, x, y + res, z) ?: getValue(grid, x, y, z) ?: 0f
        val vY0 = getValue(grid, x, y - res, z) ?: getValue(grid, x, y, z) ?: 0f
        val vZ1 = getValue(grid, x, y, z + res) ?: getValue(grid, x, y, z) ?: 0f
        val vZ0 = getValue(grid, x, y, z - res) ?: getValue(grid, x, y, z) ?: 0f
        
        val dx = (vX1 - vX0) / (2 * res)
        val dy = (vY1 - vY0) / (2 * res)
        val dz = (vZ1 - vZ0) / (2 * res)
        
        val magnitude = sqrt(dx * dx + dy * dy + dz * dz)
        return floatArrayOf(dx, dy, dz, magnitude)
    }
    
    private fun getValue(grid: KrigingGrid, x: Float, y: Float, z: Float): Float? {
        if (x < grid.minX || x > grid.maxX || y < grid.minY || y > grid.maxY || z < grid.minZ || z > grid.maxZ) return null
        val nx = ((grid.maxX - grid.minX) / grid.resolution).toInt() + 1
        val ny = ((grid.maxY - grid.minY) / grid.resolution).toInt() + 1
        
        val ix = ((x - grid.minX) / grid.resolution).toInt()
        val iy = ((y - grid.minY) / grid.resolution).toInt()
        val iz = ((z - grid.minZ) / grid.resolution).toInt()
        
        val idx = ix + iy * nx + iz * nx * ny
        if (idx < 0 || idx >= grid.values.size) return null
        return grid.values[idx]
    }
}
