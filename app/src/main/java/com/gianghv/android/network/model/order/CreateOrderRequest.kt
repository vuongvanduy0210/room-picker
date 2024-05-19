package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class CreateOrderRequest(
    @Json(name = "userId") val userId: String,
    @Json(name = "price") val price: Int,
    @Json(name = "noteBooking") val noteBooking: String,
    @Json(name = "room") val room: CreateOrderRoomRequest
)
