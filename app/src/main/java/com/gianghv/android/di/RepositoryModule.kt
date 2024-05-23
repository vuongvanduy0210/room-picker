package com.gianghv.android.di

import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.auth.AuthRepositoryImpl
import com.gianghv.android.repository.room.RoomRepository
import com.gianghv.android.repository.room.RoomRepositoryImpl
import com.gianghv.android.repository.user.UserRepository
import com.gianghv.android.repository.user.UserRepositoryImpl
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
    fun provideRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository {
        return roomRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository {
        return authRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }
}
