package com.gianghv.android.domain

import java.util.Date

data class Order(
    val id: String,
    val userId: String,
    var typePayment: TypePayment,
    val price: Int,
    var status: OrderStatus,
    val noteBooking: String,
    val roomId: String,
    val people: Int,
    val startDate: Date,
    val endDate: Date,
    val bookingDate: Date
)
