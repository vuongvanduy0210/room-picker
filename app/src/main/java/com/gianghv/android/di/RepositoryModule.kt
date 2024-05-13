package com.gianghv.android.di

import com.gianghv.android.repository.RoomRepository
import com.gianghv.android.repository.RoomRepositoryImpl
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.auth.AuthRepositoryImpl
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

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository {
        return authRepositoryImpl
    }
}
