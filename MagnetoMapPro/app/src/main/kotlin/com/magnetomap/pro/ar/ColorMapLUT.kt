package com.magnetomap.pro.ar

object ColorMapLUT {
    fun getJetColor(value: Float): Int {
        val v = value.coerceIn(0f, 1f)
        val r = (1.5f - Math.abs(4.0f * v - 3.0f)).coerceIn(0f, 1f)
        val g = (1.5f - Math.abs(4.0f * v - 2.0f)).coerceIn(0f, 1f)
        val b = (1.5f - Math.abs(4.0f * v - 1.0f)).coerceIn(0f, 1f)
        
        val ir = (r * 255).toInt()
        val ig = (g * 255).toInt()
        val ib = (b * 255).toInt()
        
        return (255 shl 24) or (ir shl 16) or (ig shl 8) or ib
    }
}
