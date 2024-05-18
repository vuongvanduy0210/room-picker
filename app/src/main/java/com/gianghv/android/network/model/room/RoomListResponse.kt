package com.gianghv.android.network.model.room

import com.gianghv.android.util.ext.toRoom
import com.squareup.moshi.Json

data class RoomListResponse(
    @Json(name = "message") val message: String,
    @Json(name = "data") val rooms: List<RoomResponse>
) {
    fun toRoomList() = rooms.map { it.toRoom() }
}
