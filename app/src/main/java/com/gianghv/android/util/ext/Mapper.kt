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
import com.gianghv.android.network.model.order.OrderDetailResponse
import com.gianghv.android.network.model.order.OrderResponse
import com.gianghv.android.network.model.order.OrderResponseAllOrder
import com.gianghv.android.network.model.room.RoomDetailResponse
import com.gianghv.android.network.model.room.RoomResponse
import com.gianghv.android.network.model.signup.SignUpResponse
import com.gianghv.android.network.model.user.UserResponse
import java.util.Date

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

fun OrderResponse.toOrder(): Order {
    val typePayment: TypePayment = try {
        TypePayment.valueOf(typePayment)
    } catch (e: IllegalArgumentException) {
        TypePayment.EMPTY
    }

    val orderStatus: OrderStatus = try {
        OrderStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        OrderStatus.PENDING
    }

    val startDate = room.startDate.dateFormatterZ()
    val endDate = room.endDate.dateFormatterZ()
    val bookingDate = createdAt?.dateFormatterZ()

    return Order(
        id,
        userId.toString(),
        typePayment,
        price,
        orderStatus,
        noteBooking,
        room.id,
        room.people,
        startDate ?: Date(),
        endDate ?: Date(),
        bookingDate ?: Date()
    )
}

fun OrderDetailResponse.toOrder(): Order {
    val typePayment: TypePayment = try {
        TypePayment.valueOf(typePayment)
    } catch (e: IllegalArgumentException) {
        TypePayment.EMPTY
    }

    val orderStatus: OrderStatus = try {
        OrderStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        OrderStatus.PENDING
    }

    val startDate = room.startDate.dateFormatterZ()
    val endDate = room.endDate.dateFormatterZ()
    val bookingDate = createdAt?.dateFormatterZ()

    return Order(
        id,
        userId?.id.toString(),
        typePayment,
        price,
        orderStatus,
        noteBooking,
        room.id,
        room.people,
        startDate ?: Date(),
        endDate ?: Date(),
        bookingDate ?: Date()
    )
}

fun OrderResponseAllOrder.toOrder(): Order {
    val typePayment: TypePayment = try {
        TypePayment.valueOf(typePayment)
    } catch (e: IllegalArgumentException) {
        TypePayment.EMPTY
    }

    val orderStatus: OrderStatus = try {
        OrderStatus.valueOf(status)
    } catch (e: IllegalArgumentException) {
        OrderStatus.PENDING
    }

    val startDate = room.startDate.dateFormatterZ()
    val endDate = room.endDate.dateFormatterZ()
    val bookingDate = createdAt?.dateFormatterZ()

    return Order(
        id,
        "",
        typePayment,
        price,
        orderStatus,
        noteBooking,
        room.id,
        room.people,
        startDate ?: Date(),
        endDate ?: Date(),
        bookingDate ?: Date()
    )
}

fun UserResponse.toUser(): User {
    return User(id, name, email, role)
}
