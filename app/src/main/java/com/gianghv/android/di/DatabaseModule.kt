package com.gianghv.android.di

import android.content.Context
import androidx.room.Room
import com.gianghv.android.database.RoomDatabase
import com.gianghv.android.database.dao.EvaluationDao
import com.gianghv.android.database.dao.ImageDao
import com.gianghv.android.database.dao.RoomDao
import com.gianghv.android.database.dao.UserDao
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            RoomDatabase::class.java,
            "Users"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideUserDao(roomDatabase: RoomDatabase): UserDao {
        return roomDatabase.usersDao
    }

    @Provides
    fun provideRoomDao(roomDatabase: RoomDatabase): RoomDao {
        return roomDatabase.roomsDao
    }

    @Provides
    fun provideEvaluationDao(roomDatabase: RoomDatabase): EvaluationDao {
        return roomDatabase.evaluationsDao
    }

    @Provides
    fun provideImageDao(roomDatabase: RoomDatabase): ImageDao {
        return roomDatabase.imagesDao
    }
}
