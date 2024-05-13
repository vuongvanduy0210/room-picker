package com.gianghv.android.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id: String,
    val name: String,
    val desc: String,
    val images: List<Image>,
    val evaluation: List<RoomEvaluation>,
    val type: String,
    val status: String,
    val countPeople: Int,
    val price: Int,
    val active: String,
    val createdAt: String,
    val updatedAt: String
) : Parcelable
