package com.gianghv.android.network.model.room

import com.gianghv.android.network.model.order.OrderResponse
import com.squareup.moshi.Json

data class SingleOrderResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: OrderResponse
)
