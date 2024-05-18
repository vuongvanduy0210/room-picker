package com.gianghv.android.network.model.room

import com.gianghv.android.network.model.evaluation.EvaluationResponse
import com.gianghv.android.network.model.photo.Photo
import com.squareup.moshi.Json

data class RoomDetailResponse(
    @Json(name = "_id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "desc") val desc: String,
    @Json(name = "image") val image: List<Photo>,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String,
    @Json(name = "type") val type: String,
    @Json(name = "status") val status: Int,
    @Json(name = "countPeople") val countPeople: Int,
    @Json(name = "price") val price: Int,
    @Json(name = "active") val active: String,
    @Json(name = "evaluate") val evaluations: List<EvaluationResponse>

)
