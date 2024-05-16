package com.gianghv.android.repository.auth

import com.gianghv.android.base.Response
import com.gianghv.android.datasource.local.LocalDataSource
import com.gianghv.android.datasource.remote.AuthDataSource
import com.gianghv.android.domain.TokenModel
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

    suspend fun saveTokenModel(tokenModel: TokenModel)

    suspend fun getTokenModel(): TokenModel?
}

class AuthRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun signIn(loginRequest: LoginRequest) = withContext(Dispatchers.IO) {
        authDataSource.signIn(loginRequest = loginRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest) = withContext(Dispatchers.IO) {
        authDataSource.signUp(signUpRequest = signUpRequest)
    }

    override suspend fun saveTokenModel(tokenModel: TokenModel) = withContext(Dispatchers.IO) {
        localDataSource.saveTokenModel(tokenModel = tokenModel)
    }

    override suspend fun getTokenModel(): TokenModel? = withContext(Dispatchers.IO) {
        return@withContext localDataSource.getTokenModel()
    }
}
