package com.gianghv.android.network.api

import com.gianghv.android.network.model.room.RoomListResponse
import com.gianghv.android.network.model.room.SingleRoomResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RoomApi {
    @GET("api/getall-room")
    suspend fun requestRoomList(): RoomListResponse

    @GET("api/get-id-room/{id}")
    suspend fun requestRoomById(@Path("id") id: String): SingleRoomResponse
}
