package com.gianghv.android.repository.auth

import com.gianghv.android.base.Response
import com.gianghv.android.network.datasource.AuthDataSource
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.network.model.signup.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRepository {
    suspend fun signIn(loginRequest: LoginRequest): Response<LoginResponse>

    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponse>
}

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signIn(loginRequest: LoginRequest) = withContext(Dispatchers.IO) {
        authDataSource.signIn(loginRequest = loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest) = withContext(Dispatchers.IO) {
        authDataSource.signUp(signUpRequest = signUpRequest)
    }
}
