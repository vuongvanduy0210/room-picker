package com.gianghv.android.network.api

import com.gianghv.android.network.model.order.CreateOrderRequest
import com.gianghv.android.network.model.order.OrderListResponse
import com.gianghv.android.network.model.order.PaymentRequest
import com.gianghv.android.network.model.room.SingleOrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderApi {
    @GET("api/getall-order")
    suspend fun getAllOrder(): OrderListResponse

    @POST("api/create-order/{roomId}")
    suspend fun createOrder(@Path("roomId") roomId: String, @Body orderRequest: CreateOrderRequest): SingleOrderResponse

    @POST("api/payment-cash/{orderId}")
    suspend fun payOrder(@Path("orderId") orderId: String, @Body paymentRequest: PaymentRequest): SingleOrderResponse
}
