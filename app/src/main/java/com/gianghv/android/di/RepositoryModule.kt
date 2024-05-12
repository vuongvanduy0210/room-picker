package com.gianghv.android.di

import com.gianghv.android.repository.RoomRepository
import com.gianghv.android.repository.RoomRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRoomRepository(): RoomRepository {
        return RoomRepositoryImpl()
    }
}
