package com.magnetomap.pro.kriging

class SpatialGrid3D(val resolution: Float) {
    private val voxels = mutableMapOf<Triple<Int, Int, Int>, Float>()
    
    fun addValue(x: Float, y: Float, z: Float, value: Float) {
        val ix = (x / resolution).toInt()
        val iy = (y / resolution).toInt()
        val iz = (z / resolution).toInt()
        voxels[Triple(ix, iy, iz)] = value
    }
    
    fun getValue(x: Float, y: Float, z: Float): Float? {
        val ix = (x / resolution).toInt()
        val iy = (y / resolution).toInt()
        val iz = (z / resolution).toInt()
        return voxels[Triple(ix, iy, iz)]
    }
    
    fun getBounds(): FloatArray {
        if (voxels.isEmpty()) return floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f)
        var minX = Float.MAX_VALUE; var maxX = -Float.MAX_VALUE
        var minY = Float.MAX_VALUE; var maxY = -Float.MAX_VALUE
        var minZ = Float.MAX_VALUE; var maxZ = -Float.MAX_VALUE
        
        for ((k, _) in voxels) {
            val x = k.first * resolution
            val y = k.second * resolution
            val z = k.third * resolution
            if (x < minX) minX = x; if (x > maxX) maxX = x
            if (y < minY) minY = y; if (y > maxY) maxY = y
            if (z < minZ) minZ = z; if (z > maxZ) maxZ = z
        }
        return floatArrayOf(minX, maxX, minY, maxY, minZ, maxZ)
    }
}
