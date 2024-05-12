package com.gianghv.android.repository.auth

import com.gianghv.android.base.Response
import com.gianghv.android.network.data_source.AuthDataSource
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.sign_up.SignUpRequest
import com.gianghv.android.network.model.sign_up.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest) = withContext(Dispatchers.IO) {
        authDataSource.login(loginRequest = loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest) = withContext(Dispatchers.IO) {
        authDataSource.signUp(signUpRequest = signUpRequest)
    }
}
