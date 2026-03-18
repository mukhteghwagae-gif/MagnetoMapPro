package com.magnetomap.pro.ar

import android.content.Context
import com.magnetomap.pro.domain.model.KrigingGrid

class ParticleSystem(private val context: Context) {
    fun init() {}
    fun updateGrid(grid: KrigingGrid) {}
    fun updateParticles(dt: Float) {}
    fun draw(viewMatrix: FloatArray, projectionMatrix: FloatArray, cameraPos: FloatArray) {}
}
