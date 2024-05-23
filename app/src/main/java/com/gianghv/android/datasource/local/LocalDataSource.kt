package com.gianghv.android.datasource.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.gianghv.android.domain.TokenModel
import com.gianghv.android.util.app.AppConstants
import com.squareup.moshi.Moshi
import javax.inject.Inject

interface LocalDataSource {
    suspend fun saveTokenModel(tokenModel: TokenModel)

    suspend fun getTokenModel(): TokenModel?

    suspend fun clearTokenModel()
}

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : LocalDataSource {

    private val moshi = Moshi.Builder().build().adapter(TokenModel::class.java)

    @SuppressLint("CommitPrefEdits")
    override suspend fun saveTokenModel(tokenModel: TokenModel) {
        val json = moshi.toJson(tokenModel)
        sharedPreferences.edit().putString(AppConstants.KEY_ACCESS_TOKEN, json).apply()
    }

    override suspend fun getTokenModel(): TokenModel? {
        val json = sharedPreferences.getString(AppConstants.KEY_ACCESS_TOKEN, null) ?: ""
        return try {
            moshi.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun clearTokenModel() {
        sharedPreferences.edit().remove(AppConstants.KEY_ACCESS_TOKEN).apply()
    }
}
