package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class OrderListResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val data: List<OrderResponseAllOrder>
)
