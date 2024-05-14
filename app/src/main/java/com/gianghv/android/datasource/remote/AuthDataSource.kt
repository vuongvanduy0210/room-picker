package com.gianghv.android.datasource.remote

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.AuthApi
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.network.model.signup.SignUpResponse
import javax.inject.Inject

interface AuthDataSource {
    suspend fun signIn(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse>
}

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : BaseRemoteDataSource(), AuthDataSource {

    override suspend fun signIn(loginRequest: LoginRequest): Response<LoginResponse> {
        return safeCallApi {
            authApi.signIn(loginRequest = loginRequest)
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse> {
        return safeCallApi {
            authApi.signUp(signUpRequest = signUpRequest)
        }
    }
}
