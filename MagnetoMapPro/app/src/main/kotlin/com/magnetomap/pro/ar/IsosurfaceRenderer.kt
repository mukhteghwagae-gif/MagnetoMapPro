package com.magnetomap.pro.ar

import android.content.Context
import com.magnetomap.pro.domain.model.KrigingGrid

class IsosurfaceRenderer(private val context: Context) {
    fun init() {}
    fun updateGrid(grid: KrigingGrid, threshold: Float) {}
    fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray, cameraPos: FloatArray) {}
}
