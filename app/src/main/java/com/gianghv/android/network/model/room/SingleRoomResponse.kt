package com.gianghv.android.network.model.room

import com.gianghv.android.util.ext.toRoom
import com.squareup.moshi.Json

data class SingleRoomResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val room: RoomDetailResponse
) {
    fun toRoom() = room.toRoom()
}
