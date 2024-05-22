package com.gianghv.android.datasource.remote

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.UserApi
import com.gianghv.android.network.model.user.SingleUserResponse
import com.gianghv.android.network.model.user.UserDetailResponse
import com.gianghv.android.network.model.user.UserUpdateRequest
import javax.inject.Inject

interface UserDataSource {
    suspend fun getUserDetail(id: String): Response<SingleUserResponse>
    suspend fun updateUser(id: String, name: String, email: String, role: String): Response<UserDetailResponse>
}

class UserDataSourceImpl @Inject constructor(
    private val userApi: UserApi
) : BaseRemoteDataSource(), UserDataSource {
    override suspend fun getUserDetail(id: String): Response<SingleUserResponse> {
        return safeCallApi {
            userApi.getUserDetail(id)
        }
    }

    override suspend fun updateUser(
        id: String,
        name: String,
        email: String,
        role: String
    ): Response<UserDetailResponse> {
        return safeCallApi {
            val user = UserUpdateRequest(name, email, role)
            userApi.updateUser(id, user)
        }
    }
}
