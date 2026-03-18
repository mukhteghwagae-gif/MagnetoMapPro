package com.magnetomap.pro.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "samples")
data class MagneticSampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val surveyId: Long,
    val x: Float,
    val y: Float,
    val z: Float,
    val bx: Float,
    val by: Float,
    val bz: Float,
    val magnitude: Float,
    val timestamp: Long,
    val isContaminated: Boolean
)
