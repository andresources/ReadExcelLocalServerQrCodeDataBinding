package com.hiltdraggerdemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudentModule {
    @Provides
    @Singleton
    fun provideStudent() = Student()
}