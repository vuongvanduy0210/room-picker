package com.gianghv.android.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomEvaluation(
    val id: String,
    val userId: String,
    val content: String,
    val star: Double,
    val images: List<Image>
) : Parcelable
