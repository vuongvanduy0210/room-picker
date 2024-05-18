package com.gianghv.android.domain

enum class RoomType {
    Normal,
    Vip
}

enum class RoomStatus {
    OK,
    NOT_OK
}

enum class RoomActive {
    InActive,
    Active
}

enum class TypePayment {
    VNPAY,
    CASH,
    EMPTY
}

enum class OrderStatus {
    PENDING,
    PAYED,
    COMPLETED,
    DEPOSIT
}
