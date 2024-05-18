package com.gianghv.android.domain

data class Order(
    val id: String,
    val userId: String,
    var typePayment: TypePayment,
    val price: Int,
    var status: OrderStatus,
    val noteBooking: String,
    val roomId: String,
    val people: Int,
    val startDate: String,
    val endDate: String
)
