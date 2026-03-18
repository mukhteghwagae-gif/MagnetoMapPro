package com.magnetomap.pro.domain.usecase

import com.magnetomap.pro.data.repository.SurveyRepository
import javax.inject.Inject

class StartSurveyUseCase @Inject constructor(
    private val repository: SurveyRepository
) {
    suspend operator fun invoke(name: String): Long {
        return repository.createSurvey(name)
    }
}
