package com.gianghv.android.network.model.signup

import com.gianghv.android.network.model.UserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResponse(
    @Json(name = "accessToken")
    val accessToken: String? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "user")
    val user: UserResponse? = null
)
