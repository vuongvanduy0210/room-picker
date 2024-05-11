package com.gianghv.android.di

import com.gianghv.android.repository.RoomRepository
import com.gianghv.android.repository.RoomRepositoryImpl
import dagger.Provides
import javax.inject.Singleton

object RepositoryModule {
    @Provides
    @Singleton
    fun provideRoomRepository(): RoomRepository {
        return RoomRepositoryImpl()
    }
}
