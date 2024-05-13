package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.Room

@Entity
data class DatabaseRoom(
    @PrimaryKey val id: String,
    val name: String,
    val desc: String,
    val type: String,
    val status: String,
    val countPeople: Int,
    val price: Int,
    val active: String,
    val createdAt: String,
    val updatedAt: String
)

fun DatabaseRoom.asDomainModel(): Room {
    return Room(
        id, name, desc, mutableListOf(), mutableListOf(), type, status, countPeople, price, active, createdAt, updatedAt
    )
}

fun List<DatabaseRoom>.asDomainModel(): List<Room> {
    return map {
        Room(
            it.id,
            it.name,
            it.desc,
            mutableListOf(),
            mutableListOf(),
            it.type,
            it.status,
            it.countPeople,
            it.price,
            it.active,
            it.createdAt,
            it.updatedAt
        )
    }
}
