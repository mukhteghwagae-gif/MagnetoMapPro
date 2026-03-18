package com.magnetomap.pro.domain.usecase

import com.magnetomap.pro.domain.model.AnomalyDetection
import com.magnetomap.pro.domain.model.KrigingGrid
import com.magnetomap.pro.kriging.GradientComputer
import com.magnetomap.pro.ml.AnomalyClassifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.math.sqrt

class DetectAnomaliesUseCase @Inject constructor(
    private val classifier: AnomalyClassifier
) {
    private val gradientComputer = GradientComputer()

    suspend operator fun invoke(grid: KrigingGrid, threshold: Float): List<AnomalyDetection> = withContext(Dispatchers.Default) {
        val anomalies = mutableListOf<AnomalyDetection>()
        val res = grid.resolution
        val nx = ((grid.maxX - grid.minX) / res).toInt() + 1
        val ny = ((grid.maxY - grid.minY) / res).toInt() + 1
        val nz = ((grid.maxZ - grid.minZ) / res).toInt() + 1

        for (iz in 1 until nz - 1) {
            for (iy in 1 until ny - 1) {
                for (ix in 1 until nx - 1) {
                    val idx = ix + iy * nx + iz * nx * ny
                    val value = grid.values[idx]
                    
                    if (value > threshold) {
                        val isMaxima = 
                            value > grid.values[(ix+1) + iy * nx + iz * nx * ny] &&
                            value > grid.values[(ix-1) + iy * nx + iz * nx * ny] &&
                            value > grid.values[ix + (iy+1) * nx + iz * nx * ny] &&
                            value > grid.values[ix + (iy-1) * nx + iz * nx * ny] &&
                            value > grid.values[ix + iy * nx + (iz+1) * nx * ny] &&
                            value > grid.values[ix + iy * nx + (iz-1) * nx * ny]
                            
                        if (isMaxima) {
                            val x = grid.minX + ix * res
                            val y = grid.minY + iy * res
                            val z = grid.minZ + iz * res
                            
                            val grad = gradientComputer.computeGradient(grid, x, y, z)
                            val variance = grid.variances[idx]
                            
                            val features = floatArrayOf(
                                value,
                                res * 2,
                                res * 2,
                                res * 2,
                                grad[3],
                                0.8f,
                                variance,
                                1.5f,
                                1.0f,
                                0.0f,
                                grad[2],
                                sqrt(grad[0]*grad[0] + grad[1]*grad[1])
                            )
                            
                            val (classification, confidence) = classifier.classify(features)
                            
                            anomalies.add(
                                AnomalyDetection(
                                    id = UUID.randomUUID().toString(),
                                    x = x, y = y, z = z,
                                    peakMagnitude = value,
                                    classification = classification,
                                    confidence = confidence,
                                    depthEstimate = 1.5f
                                )
                            )
                        }
                    }
                }
            }
        }
        anomalies
    }
}
