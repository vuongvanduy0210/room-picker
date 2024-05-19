package com.gianghv.android.network.model.user

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "_id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "role")
    val role: String
)
