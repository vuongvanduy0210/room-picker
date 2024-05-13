package com.gianghv.android.database.table

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gianghv.android.domain.Image

@Entity
data class DatabaseEvaluationImage(
    @PrimaryKey val id: String,
    val evaluationId: String,
    val url: String
)

fun DatabaseEvaluationImage.asDomainModel(): Image {
    return Image(id, url)
}

fun List<DatabaseEvaluationImage>.asDomainModel(): List<Image> {
    return map { Image(it.id, it.url) }
}
