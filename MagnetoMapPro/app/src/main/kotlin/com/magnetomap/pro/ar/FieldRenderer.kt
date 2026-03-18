package com.magnetomap.pro.ar

import android.content.Context
import com.magnetomap.pro.domain.model.KrigingGrid

class FieldRenderer(private val context: Context) {
    private var program = 0
    private var textureId = 0

    fun init() {}

    fun updateGrid(grid: KrigingGrid) {}

    fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray, cameraPos: FloatArray) {}
}
