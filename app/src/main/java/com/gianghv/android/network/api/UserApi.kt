package com.gianghv.android.network.api

import com.gianghv.android.network.model.user.SingleUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("api/get-user/{uid}")
    suspend fun getUserDetail(@Path("uid") uid: String): SingleUserResponse
}
