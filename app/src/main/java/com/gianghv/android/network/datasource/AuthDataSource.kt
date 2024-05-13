package com.gianghv.android.network.datasource

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.AuthApi
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.network.model.signup.SignUpResponse
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authApi: AuthApi
) : BaseRemoteDataSource() {

    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return safeCallApi {
            authApi.login(loginRequest = loginRequest)
        }
    }

    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse> {
        return safeCallApi {
            authApi.signUp(signUpRequest = signUpRequest)
        }
    }
}
