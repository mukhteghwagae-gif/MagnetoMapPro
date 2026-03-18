package com.magnetomap.pro.di

import com.magnetomap.pro.data.repository.SurveyRepository
import com.magnetomap.pro.data.repository.SurveyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSurveyRepository(
        surveyRepositoryImpl: SurveyRepositoryImpl
    ): SurveyRepository
}
