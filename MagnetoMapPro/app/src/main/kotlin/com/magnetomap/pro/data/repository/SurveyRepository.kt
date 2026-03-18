package com.magnetomap.pro.data.repository

import com.magnetomap.pro.domain.model.MagneticSample
import com.magnetomap.pro.domain.model.Survey
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun createSurvey(name: String): Long
    suspend fun updateSurvey(survey: Survey)
    suspend fun saveSamples(surveyId: Long, samples: List<MagneticSample>)
    fun getAllSurveys(): Flow<List<Survey>>
    suspend fun getSurvey(id: Long): Survey?
    suspend fun getSamples(surveyId: Long): List<MagneticSample>
    suspend fun deleteSurvey(id: Long)
}
