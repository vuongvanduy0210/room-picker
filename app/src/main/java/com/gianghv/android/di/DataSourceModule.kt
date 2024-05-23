package com.gianghv.android.di

import android.content.Context
import android.content.SharedPreferences
import com.gianghv.android.datasource.local.LocalDataSource
import com.gianghv.android.datasource.local.LocalDataSourceImpl
import com.gianghv.android.datasource.remote.AuthDataSource
import com.gianghv.android.datasource.remote.AuthDataSourceImpl
import com.gianghv.android.datasource.remote.OrderDataSource
import com.gianghv.android.datasource.remote.OrderDataSourceImpl
import com.gianghv.android.datasource.remote.RoomDataSource
import com.gianghv.android.datasource.remote.RoomDataSourceImpl
import com.gianghv.android.datasource.remote.UserDataSource
import com.gianghv.android.datasource.remote.UserDataSourceImpl
import com.gianghv.android.util.app.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource {
        return authDataSourceImpl
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource {
        return localDataSourceImpl
    }

    @Provides
    @Singleton
    fun provideRoomDataSource(roomDataSourceImpl: RoomDataSourceImpl): RoomDataSource {
        return roomDataSourceImpl
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideOrderDataSource(orderDataSourceImpl: OrderDataSourceImpl): OrderDataSource {
        return orderDataSourceImpl
    }

    @Provides
    @Singleton
    fun provideUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource {
        return userDataSourceImpl
    }
}
