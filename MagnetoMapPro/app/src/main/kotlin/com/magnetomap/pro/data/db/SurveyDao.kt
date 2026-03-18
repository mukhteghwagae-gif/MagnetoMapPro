package com.magnetomap.pro.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.magnetomap.pro.data.db.entities.SurveyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SurveyDao {
    @Insert
    suspend fun insert(survey: SurveyEntity): Long

    @Update
    suspend fun update(survey: SurveyEntity)

    @Query("SELECT * FROM surveys ORDER BY startTime DESC")
    fun getAllSurveys(): Flow<List<SurveyEntity>>

    @Query("SELECT * FROM surveys WHERE id = :id")
    suspend fun getSurveyById(id: Long): SurveyEntity?
    
    @Query("DELETE FROM surveys WHERE id = :id")
    suspend fun deleteSurvey(id: Long)
}
