package com.gianghv.android.network.model.photo

import com.gianghv.android.domain.Image
import com.squareup.moshi.Json

data class Photo(
    @Json(name = "_id") val id: String,
    @Json(name = "url") val url: String
) {
    fun toDomain() = Image(id, url)
}
