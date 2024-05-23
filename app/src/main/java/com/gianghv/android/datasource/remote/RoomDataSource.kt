package com.gianghv.android.datasource.remote

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.EvaluationApi
import com.gianghv.android.network.api.RoomApi
import com.gianghv.android.network.model.evaluation.CreateEvaluationRequest
import com.gianghv.android.network.model.room.RoomListResponse
import com.gianghv.android.network.model.room.SingleRoomResponse
import javax.inject.Inject

interface RoomDataSource {
    suspend fun getAllRooms(): Response<RoomListResponse>
    suspend fun getRoomDetail(id: String): Response<SingleRoomResponse>

    suspend fun createEvaluation(id: String, evaluation: CreateEvaluationRequest): Response<Any>
}

class RoomDataSourceImpl @Inject constructor(
    private val roomApi: RoomApi,
    private val evaluationApi: EvaluationApi
) : BaseRemoteDataSource(), RoomDataSource {
    override suspend fun getAllRooms(): Response<RoomListResponse> {
        return safeCallApi {
            roomApi.requestRoomList()
        }
    }

    override suspend fun getRoomDetail(id: String): Response<SingleRoomResponse> {
        return safeCallApi {
            roomApi.requestRoomById(id)
        }
    }

    override suspend fun createEvaluation(id: String, evaluation: CreateEvaluationRequest): Response<Any> {
        return safeCallApi {
            evaluationApi.createEvaluation(id, evaluation)
        }
    }
}
