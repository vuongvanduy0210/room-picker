package com.gianghv.android.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.round

@Parcelize
data class Room(
    val id: String,
    val name: String,
    val desc: String,
    val images: List<Image>,
    val evaluation: List<RoomEvaluation>,
    val type: RoomType,
    val status: RoomStatus,
    val countPeople: Int,
    val price: Int,
    val active: RoomActive,
    val createdAt: String,
    val updatedAt: String
) : Parcelable {
    fun getEvaluationAverage(): Double {
        val average = evaluation.map { it.star }.average()
        // Round to 1 digit
        return round(average * 10) / 10
    }
}
