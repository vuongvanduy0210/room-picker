package com.gianghv.android.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoomEvaluation(
    val id: String,
    val content: String,
    val star: Int,
    val images: List<Image>
) : Parcelable
