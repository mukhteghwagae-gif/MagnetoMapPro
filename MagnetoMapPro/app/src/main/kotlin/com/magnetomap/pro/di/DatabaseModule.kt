package com.magnetomap.pro.di

import android.content.Context
import androidx.room.Room
import com.magnetomap.pro.data.db.MagnetoDatabase
import com.magnetomap.pro.data.db.SampleDao
import com.magnetomap.pro.data.db.SurveyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MagnetoDatabase {
        return Room.databaseBuilder(
            context,
            MagnetoDatabase::class.java,
            "magnetomap_db"
        ).build()
    }

    @Provides
    fun provideSurveyDao(db: MagnetoDatabase): SurveyDao = db.surveyDao()

    @Provides
    fun provideSampleDao(db: MagnetoDatabase): SampleDao = db.sampleDao()
}
