package com.magnetomap.pro.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magnetomap.pro.data.db.entities.MagneticSampleEntity
import com.magnetomap.pro.data.db.entities.SurveyEntity

@Database(entities = [SurveyEntity::class, MagneticSampleEntity::class], version = 1, exportSchema = false)
abstract class MagnetoDatabase : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
    abstract fun sampleDao(): SampleDao
}
