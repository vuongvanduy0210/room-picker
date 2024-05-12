package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.RoomEvaluation

@Entity
data class DatabaseRoomEvaluation(
    @PrimaryKey
    val id: String,
    val content: String,
    val star: Int
)

fun DatabaseRoomEvaluation.asDatabaseModel(): RoomEvaluation {
    return RoomEvaluation(id, content, star, mutableListOf())
}

fun List<DatabaseRoomEvaluation>.asDomainModel(): List<RoomEvaluation> {
    return map {
        RoomEvaluation(it.id, it.content, it.star, mutableListOf())
    }
}
