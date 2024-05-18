package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomActive
import com.gianghv.android.domain.RoomStatus
import com.gianghv.android.domain.RoomType

@Entity
data class DatabaseRoom(
    @PrimaryKey val id: String,
    val name: String,
    val desc: String,
    val type: String,
    val status: Int,
    val countPeople: Int,
    val price: Int,
    val active: String,
    val createdAt: String,
    val updatedAt: String
)

fun DatabaseRoom.asDomainModel(): Room {
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

    return Room(
        id,
        name,
        desc,
        mutableListOf(),
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

fun List<DatabaseRoom>.asDomainModel(): List<Room> {
    return map {
        it.asDomainModel()
    }
}
