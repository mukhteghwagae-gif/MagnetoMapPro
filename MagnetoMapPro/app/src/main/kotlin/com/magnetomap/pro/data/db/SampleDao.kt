package com.magnetomap.pro.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.magnetomap.pro.data.db.entities.MagneticSampleEntity

@Dao
interface SampleDao {
    @Insert
    suspend fun insertAll(samples: List<MagneticSampleEntity>)

    @Query("SELECT * FROM samples WHERE surveyId = :surveyId ORDER BY timestamp ASC")
    suspend fun getSamplesForSurvey(surveyId: Long): List<MagneticSampleEntity>
    
    @Query("DELETE FROM samples WHERE surveyId = :surveyId")
    suspend fun deleteSamplesForSurvey(surveyId: Long)
}
