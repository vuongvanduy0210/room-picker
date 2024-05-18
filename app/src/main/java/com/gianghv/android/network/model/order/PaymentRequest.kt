package com.gianghv.android.network.model.order

import com.squareup.moshi.Json

data class PaymentRequest(
    @Json(name = "status")
    val status: String,
    @Json(name = "typePayment")
    val paymentMethod: String
)
