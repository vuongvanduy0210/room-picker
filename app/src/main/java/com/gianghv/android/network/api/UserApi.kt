package com.gianghv.android.network.api

import com.gianghv.android.network.model.user.SingleUserResponse
import com.gianghv.android.network.model.user.UserDetailResponse
import com.gianghv.android.network.model.user.UserUpdateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("api/get-user/{uid}")
    suspend fun getUserDetail(@Path("uid") uid: String): SingleUserResponse

    @DELETE("api/delete-user/{id}")
    suspend fun deleteUser(@Path("id") id: String): UserDetailResponse

    @PUT("api/update-user/{id}")
    suspend fun updateUser(@Path("id") id: String, @Body user: UserUpdateRequest): UserDetailResponse

}
