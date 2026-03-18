package com.magnetomap.pro.domain.usecase

import com.magnetomap.pro.data.repository.SurveyRepository
import com.magnetomap.pro.domain.model.MagneticSample
import com.magnetomap.pro.domain.model.Survey
import javax.inject.Inject

class StopSurveyUseCase @Inject constructor(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(surveyId: Long, samples: List<MagneticSample>) {
        val survey = repository.getSurvey(surveyId) ?: return
        
        var minX = Float.MAX_VALUE; var maxX = -Float.MAX_VALUE
        var minY = Float.MAX_VALUE; var maxY = -Float.MAX_VALUE
        var minZ = Float.MAX_VALUE; var maxZ = -Float.MAX_VALUE
        
        samples.forEach {
            if (it.x < minX) minX = it.x; if (it.x > maxX) maxX = it.x
            if (it.y < minY) minY = it.y; if (it.y > maxY) maxY = it.y
            if (it.z < minZ) minZ = it.z; if (it.z > maxZ) maxZ = it.z
        }

        val updatedSurvey = survey.copy(
            endTime = System.currentTimeMillis(),
            sampleCount = samples.size,
            minX = minX, maxX = maxX,
            minY = minY, maxY = maxY,
            minZ = minZ, maxZ = maxZ
        )
        
        repository.updateSurvey(updatedSurvey)
        repository.saveSamples(surveyId, samples)
    }
}
