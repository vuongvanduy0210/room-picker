package com.gianghv.android.network.api

import com.gianghv.android.network.model.evaluation.CreateEvaluationRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface EvaluationApi {
    @POST("api/evaluate-room/{roomId}")
    suspend fun createEvaluation(@Path("roomId") roomId: String, @Body evaluation: CreateEvaluationRequest)
}
