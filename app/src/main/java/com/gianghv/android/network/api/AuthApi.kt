package com.gianghv.android.network.api

import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.network.model.signup.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse
}
