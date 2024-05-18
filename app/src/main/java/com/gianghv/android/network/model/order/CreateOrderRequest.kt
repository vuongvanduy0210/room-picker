package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class CreateOrderRequest(
    @Json(name = "userId") val userId: String,
    @Json(name = "price") val price: Int,
    @Json(name = "noteBooking") val noteBooking: String,
    @Json(name = "startDate") val startDate: String,
    @Json(name = "endDate") val endDate: String,
    @Json(name = "people") val people: Int
)
