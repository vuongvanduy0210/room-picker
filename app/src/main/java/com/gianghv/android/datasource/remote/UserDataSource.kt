package com.gianghv.android.datasource.remote

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.UserApi
import com.gianghv.android.network.model.user.SingleUserResponse
import javax.inject.Inject

interface UserDataSource {
    suspend fun getUserDetail(id: String): Response<SingleUserResponse>
}

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : BaseRemoteDataSource(), UserDataSource {
    override suspend fun getUserDetail(id: String): Response<SingleUserResponse> {
        return safeCallApi {
            userApi.getUserDetail(id)
        }
    }

}
