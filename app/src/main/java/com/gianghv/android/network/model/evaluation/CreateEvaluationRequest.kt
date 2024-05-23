package com.gianghv.android.network.model.evaluation

import com.squareup.moshi.Json

data class CreateEvaluationRequest(
    @Json(name = "star") val star: Double,
    @Json(name = "content") val comment: String,
    @Json(name = "idUser") val userId: String,
    @Json(name = "image") val images: List<Image>
)

data class Image(
    @Json(name = "url") val url: String
)
