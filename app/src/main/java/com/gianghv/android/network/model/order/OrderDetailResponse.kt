package com.gianghv.android.network.model.order

import com.gianghv.android.network.model.room.RoomOrderResponse
import com.gianghv.android.network.model.user.UserResponse
import com.squareup.moshi.Json

data class OrderDetailResponse(
    @Json(name = "_id") val id: String,
    @Json(name = "status") val status: String,
    @Json(name = "userId") val userId: UserResponse?,
    @Json(name = "typePayment") val typePayment: String,
    @Json(name = "price") val price: Int,
    @Json(name = "createdAt") val createdAt: String?,
    @Json(name = "updatedAt") val updatedAt: String?,
    @Json(name = "noteBooking") val noteBooking: String,
    @Json(name = "room") val room: RoomOrderResponse
)
