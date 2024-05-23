package com.gianghv.android.network.model.user

import com.squareup.moshi.Json

data class SingleUserResponse(
    @Json(name = "user") val user: UserResponse
)
