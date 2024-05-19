package com.gianghv.android.datasource.remote

import com.gianghv.android.base.BaseRemoteDataSource
import com.gianghv.android.base.Response
import com.gianghv.android.network.api.OrderApi
import com.gianghv.android.network.model.order.CreateOrderRequest
import com.gianghv.android.network.model.order.OrderDetailApiResponse
import com.gianghv.android.network.model.order.OrderListResponse
import com.gianghv.android.network.model.order.PaymentRequest
import com.gianghv.android.network.model.room.SingleOrderResponse
import javax.inject.Inject

interface OrderDataSource {
    suspend fun getAllOrders(): Response<OrderListResponse?>
    suspend fun createOrder(roomId: String, request: CreateOrderRequest): Response<SingleOrderResponse?>
    suspend fun payOrder(orderId: String, paymentRequest: PaymentRequest): Response<SingleOrderResponse?>
    suspend fun getOrderByUid(uid: String): Response<OrderListResponse?>
    suspend fun getOrderDetail(orderId: String): Response<OrderDetailApiResponse?>
}

class OrderDataSourceImpl @Inject constructor(
    private val orderApi: OrderApi
) : BaseRemoteDataSource(), OrderDataSource {
    override suspend fun getAllOrders(): Response<OrderListResponse> {
        return safeCallApi {
            orderApi.getAllOrder()
        }
    }

    override suspend fun createOrder(roomId: String, request: CreateOrderRequest): Response<SingleOrderResponse> {
        return safeCallApi {
            orderApi.createOrder(roomId, request)
        }
    }

    override suspend fun payOrder(orderId: String, paymentRequest: PaymentRequest): Response<SingleOrderResponse?> {
        return safeCallApi {
            orderApi.payOrder(orderId, paymentRequest)
        }
    }

    override suspend fun getOrderByUid(uid: String): Response<OrderListResponse?> {
        return safeCallApi {
            orderApi.getOrderByUserId(uid)
        }
    }

    override suspend fun getOrderDetail(orderId: String): Response<OrderDetailApiResponse?> {
        return safeCallApi {
            orderApi.getOrderDetail(orderId)
        }
    }
}
