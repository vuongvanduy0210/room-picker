package com.gianghv.android.network.model.evaluation

import com.gianghv.android.domain.RoomEvaluation
import com.gianghv.android.network.model.photo.Photo
import com.squareup.moshi.Json

data class EvaluationResponse(
    @Json(name = "_id") val id: String,
    @Json(name = "idUser") val userId: String,
    @Json(name = "content") val content: String,
    @Json(name = "star") val star: Double,
    @Json(name = "image") val images: List<Photo>
) {
    fun toEvaluation(): RoomEvaluation {
        val imageList = images.map { it.toDomain() }
        return RoomEvaluation(id, userId, content, star, imageList)
    }
}
