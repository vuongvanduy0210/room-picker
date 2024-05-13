package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.Image

@Entity
data class DatabaseRoomImage(
    @PrimaryKey val id: String,
    val roomId: String,
    val url: String
)

fun DatabaseRoomImage.asDomainModel(): Image {
    return Image(id, url)
}

fun List<DatabaseRoomImage>.asDatabaseModel(): List<Image> {
    return map { Image(it.id, it.url) }
}
