package com.gianghv.android.network.model.login

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "message")
    val message: String,

    @Json(name = "accessToken")
    val token: String,

    @Json(name = "refreshToken")
    val refreshToken: String
)

data class User(
    @Json(name = "_id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "role")
    val role: String,
)
