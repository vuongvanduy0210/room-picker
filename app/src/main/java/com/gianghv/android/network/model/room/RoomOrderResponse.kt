package com.gianghv.android.network.model.room

import com.gianghv.android.network.model.photo.Photo
import com.squareup.moshi.Json

data class RoomOrderResponse(
    @Json(name = "idRoom") val id: String,
    @Json(name = "nameRoom") val name: String,
    @Json(name = "desc") val desc: String,
    @Json(name = "imageRoom") val image: List<Photo>,
    @Json(name = "typeRoom") val type: String,
    @Json(name = "countPeople") val countPeople: Int,
    @Json(name = "people") val people: Int,
    @Json(name = "piceRoom") val price: Int,
    @Json(name = "startDate") val startDate: String,
    @Json(name = "endDate") val endDate: String
)
