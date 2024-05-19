package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class OrderDetailApiResponse(
    @Json(name = "data")
    val order: OrderDetailResponse
)
