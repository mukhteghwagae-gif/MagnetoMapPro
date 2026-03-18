package com.magnetomap.pro.data.repository

import com.magnetomap.pro.data.db.SampleDao
import com.magnetomap.pro.data.db.SurveyDao
import com.magnetomap.pro.data.db.entities.MagneticSampleEntity
import com.magnetomap.pro.data.db.entities.SurveyEntity
import com.magnetomap.pro.domain.model.MagneticSample
import com.magnetomap.pro.domain.model.Survey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyDao: SurveyDao,
    private val sampleDao: SampleDao
) : SurveyRepository {

    override suspend fun createSurvey(name: String): Long {
        val entity = SurveyEntity(
            name = name,
            startTime = System.currentTimeMillis(),
            endTime = null,
            sampleCount = 0,
            minX = 0f, maxX = 0f, minY = 0f, maxY = 0f, minZ = 0f, maxZ = 0f
        )
        return surveyDao.insert(entity)
    }

    override suspend fun updateSurvey(survey: Survey) {
        surveyDao.update(
            SurveyEntity(
                id = survey.id,
                name = survey.name,
                startTime = survey.startTime,
                endTime = survey.endTime,
                sampleCount = survey.sampleCount,
                minX = survey.minX, maxX = survey.maxX,
                minY = survey.minY, maxY = survey.maxY,
                minZ = survey.minZ, maxZ = survey.maxZ
            )
        )
    }

    override suspend fun saveSamples(surveyId: Long, samples: List<MagneticSample>) {
        val entities = samples.map {
            MagneticSampleEntity(
                surveyId = surveyId,
                x = it.x, y = it.y, z = it.z,
                bx = it.bx, by = it.by, bz = it.bz,
                magnitude = it.magnitude,
                timestamp = it.timestamp,
                isContaminated = it.isContaminated
            )
        }
        sampleDao.insertAll(entities)
    }

    override fun getAllSurveys(): Flow<List<Survey>> {
        return surveyDao.getAllSurveys().map { list ->
            list.map { entity ->
                Survey(
                    id = entity.id,
                    name = entity.name,
                    startTime = entity.startTime,
                    endTime = entity.endTime,
                    sampleCount = entity.sampleCount,
                    minX = entity.minX, maxX = entity.maxX,
                    minY = entity.minY, maxY = entity.maxY,
                    minZ = entity.minZ, maxZ = entity.maxZ
                )
            }
        }
    }

    override suspend fun getSurvey(id: Long): Survey? {
        val entity = surveyDao.getSurveyById(id) ?: return null
        return Survey(
            id = entity.id,
            name = entity.name,
            startTime = entity.startTime,
            endTime = entity.endTime,
            sampleCount = entity.sampleCount,
            minX = entity.minX, maxX = entity.maxX,
            minY = entity.minY, maxY = entity.maxY,
            minZ = entity.minZ, maxZ = entity.maxZ
        )
    }

    override suspend fun getSamples(surveyId: Long): List<MagneticSample> {
        return sampleDao.getSamplesForSurvey(surveyId).map {
            MagneticSample(
                x = it.x, y = it.y, z = it.z,
                bx = it.bx, by = it.by, bz = it.bz,
                magnitude = it.magnitude,
                timestamp = it.timestamp,
                isContaminated = it.isContaminated
            )
        }
    }

    override suspend fun deleteSurvey(id: Long) {
        sampleDao.deleteSamplesForSurvey(id)
        surveyDao.deleteSurvey(id)
    }
}
