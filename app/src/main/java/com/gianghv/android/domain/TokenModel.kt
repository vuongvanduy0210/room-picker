package com.gianghv.android.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class TokenModel(
    val accessToken: String,
    val uid: String
)
