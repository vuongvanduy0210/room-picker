package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class CreateOrderRoomRequest(
    @Json(name = "startDate") val startDate: String,
    @Json(name = "endDate") val endDate: String,
    @Json(name = "people") val people: Int
)
