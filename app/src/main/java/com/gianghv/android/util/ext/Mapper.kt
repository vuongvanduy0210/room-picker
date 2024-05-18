package com.gianghv.android.util.ext

import com.gianghv.android.domain.Order
import com.gianghv.android.domain.OrderStatus
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomActive
import com.gianghv.android.domain.RoomStatus
import com.gianghv.android.domain.RoomType
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.domain.User
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.order.OrderResponse
import com.gianghv.android.network.model.order.OrderResponseAllOrder
import com.gianghv.android.network.model.room.RoomDetailResponse
import com.gianghv.android.network.model.room.RoomResponse
import com.gianghv.android.network.model.signup.SignUpResponse

fun LoginResponse.toUser() = User(
    id = user?.id ?: "",
    name = user?.name ?: "",
    email = user?.email ?: "",
    role = user?.role ?: ""
)

fun SignUpResponse.toUser() = User(
    id = user?.id ?: "",
    name = user?.name ?: "",
    email = user?.email ?: "",
    role = user?.role ?: ""
)

fun RoomResponse.toRoom(): Room {
    // convert type
    var roomType: RoomType
    try {
        roomType = RoomType.valueOf(type)
    } catch (e: IllegalArgumentException) {
        roomType = RoomType.Normal
    }

    // convert status
    val roomStatus = when (status) {
        0 -> RoomStatus.OK
        1 -> RoomStatus.NOT_OK
        else -> RoomStatus.OK
    }

    // convert active
    var roomActive = RoomActive.Active
    try {
        roomActive = RoomActive.valueOf(active)
    } catch (e: IllegalArgumentException) {
        roomActive = RoomActive.Active
    }

    val imageList = image.map { it.toDomain() }

    return Room(
        id,
        name,
        desc,
        imageList,
        mutableListOf(),
        roomType,
        roomStatus,
        countPeople,
        price,
        roomActive,
        createdAt,
        updatedAt
    )
}

fun RoomDetailResponse.toRoom(): Room {
    // convert type
    var roomType: RoomType
    try {
        roomType = RoomType.valueOf(type)
    } catch (e: IllegalArgumentException) {
        roomType = RoomType.Normal
    }

    // convert status
    val roomStatus = when (status) {
        0 -> RoomStatus.OK
        1 -> RoomStatus.NOT_OK
        else -> RoomStatus.OK
    }

    // convert active
    var roomActive = RoomActive.Active
    try {
        roomActive = RoomActive.valueOf(active)
    } catch (e: IllegalArgumentException) {
        roomActive = RoomActive.Active
    }

    val imageList = image.map { it.toDomain() }

    return Room(
        id,
        name,
        desc,
        imageList,
        evaluations.map { it.toEvaluation() },
        roomType,
        roomStatus,
        countPeople,
        price,
        roomActive,
        createdAt,
        updatedAt
    )
}

// TODO: change this
fun OrderResponse.toOrder(): Order {
    val typePayment = TypePayment.EMPTY
    var orderStatus: OrderStatus
    try {
        orderStatus = OrderStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        orderStatus = OrderStatus.PENDING
    }
    return Order(
        id,
        userId.toString(),
        typePayment,
        price,
        orderStatus,
        noteBooking,
        room.id,
        room.people,
        room.startDate,
        room.endDate
    )
}

fun OrderResponseAllOrder.toOrder(): Order {
    val typePayment = TypePayment.EMPTY
    var orderStatus: OrderStatus
    try {
        orderStatus = OrderStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        orderStatus = OrderStatus.PENDING
    }
    return Order(
        id,
        "",
        typePayment,
        price,
        orderStatus,
        noteBooking,
        room.id,
        room.people,
        room.startDate,
        room.endDate
    )
}
