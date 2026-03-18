package com.magnetomap.pro.domain.usecase

import com.magnetomap.pro.domain.model.KrigingGrid
import com.magnetomap.pro.domain.model.MagneticSample
import com.magnetomap.pro.kriging.KrigingInterpolator
import com.magnetomap.pro.kriging.Variogram
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InterpolateFieldUseCase @Inject constructor() {
    
    suspend operator fun invoke(samples: List<MagneticSample>, resolution: Float): KrigingGrid = withContext(Dispatchers.Default) {
        if (samples.isEmpty()) return@withContext KrigingGrid(resolution, 0f, 0f, 0f, 0f, 0f, 0f, FloatArray(0), FloatArray(0))
        
        var minX = Float.MAX_VALUE; var maxX = -Float.MAX_VALUE
        var minY = Float.MAX_VALUE; var maxY = -Float.MAX_VALUE
        var minZ = Float.MAX_VALUE; var maxZ = -Float.MAX_VALUE
        
        samples.forEach {
            if (it.x < minX) minX = it.x; if (it.x > maxX) maxX = it.x
            if (it.y < minY) minY = it.y; if (it.y > maxY) maxY = it.y
            if (it.z < minZ) minZ = it.z; if (it.z > maxZ) maxZ = it.z
        }
        
        minX -= resolution; maxX += resolution
        minY -= resolution; maxY += resolution
        minZ -= resolution; maxZ += resolution

        val nx = ((maxX - minX) / resolution).toInt() + 1
        val ny = ((maxY - minY) / resolution).toInt() + 1
        val nz = ((maxZ - minZ) / resolution).toInt() + 1
        
        val totalVoxels = nx * ny * nz
        val values = FloatArray(totalVoxels)
        val variances = FloatArray(totalVoxels)
        
        val interpolator = KrigingInterpolator(Variogram())

        for (iz in 0 until nz) {
            val z = minZ + iz * resolution
            for (iy in 0 until ny) {
                val y = minY + iy * resolution
                for (ix in 0 until nx) {
                    val x = minX + ix * resolution
                    val (est, varc) = interpolator.interpolate(samples, x, y, z)
                    val idx = ix + iy * nx + iz * nx * ny
                    values[idx] = est
                    variances[idx] = varc
                }
            }
        }
        
        KrigingGrid(resolution, minX, maxX, minY, maxY, minZ, maxZ, values, variances)
    }
}
