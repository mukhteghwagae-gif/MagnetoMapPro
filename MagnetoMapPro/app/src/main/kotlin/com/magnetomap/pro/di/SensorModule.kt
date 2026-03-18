package com.magnetomap.pro.di

import android.content.Context
import com.magnetomap.pro.sensor.MagnetometerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {
    @Provides
    @Singleton
    fun provideMagnetometerManager(@ApplicationContext context: Context): MagnetometerManager {
        return MagnetometerManager(context)
    }
}
