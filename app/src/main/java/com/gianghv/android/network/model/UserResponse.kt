package com.gianghv.android.network.model

import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "createdAt")
    val createdAt: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "_id")
    val id: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "order")
    val order: List<Any?>? = null,
    @Json(name = "role")
    val role: String? = null,
    @Json(name = "updatedAt")
    val updatedAt: String? = null
)
