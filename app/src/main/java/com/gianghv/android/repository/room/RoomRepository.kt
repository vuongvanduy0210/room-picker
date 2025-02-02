package com.gianghv.android.repository.room

import com.gianghv.android.datasource.local.LocalDataSource
import com.gianghv.android.datasource.remote.OrderDataSource
import com.gianghv.android.datasource.remote.RoomDataSource
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomEvaluation
import com.gianghv.android.network.model.evaluation.CreateEvaluationRequest
import com.gianghv.android.network.model.evaluation.Image
import com.gianghv.android.network.model.order.CreateOrderRequest
import com.gianghv.android.network.model.order.CreateOrderRoomRequest
import com.gianghv.android.network.model.order.PaymentRequest
import com.gianghv.android.util.ext.parseDateZ
import com.gianghv.android.util.ext.toOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

interface RoomRepository {
    fun requestAllRoom(): Flow<List<Room>>
    fun requestRoom(id: String): Flow<Room?>

    fun getDetailRoomList(id: List<String>): Flow<List<Room>>

    fun getAllOrder(): Flow<List<Order>>

    fun createOrder(order: Order): Flow<Order?>

    fun payOrder(order: Order): Flow<Order?>
    fun getOrderByUid(uid: String): Flow<List<Order>>
    fun getOrderDetail(id: String): Flow<Order?>

    fun createEvaluate(id: String, evaluate: RoomEvaluation): Flow<Boolean>
}

class RoomRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val roomDataSource: RoomDataSource,
    private val orderDataSource: OrderDataSource
) : RoomRepository {
    override fun requestAllRoom(): Flow<List<Room>> = flow {
        val response = roomDataSource.getAllRooms()
        val rooms = response.data
        Timber.d("messs: ${response.message}")
        if (rooms != null) {
            emit(rooms.toRoomList())
        } else {
            emit(mutableListOf())
        }
    }

    override fun requestRoom(id: String): Flow<Room?> = flow {
        val room = roomDataSource.getRoomDetail(id).data
        if (room != null) {
            emit(room.toRoom())
        } else {
            emit(null)
        }
    }

    override fun getDetailRoomList(id: List<String>): Flow<List<Room>> = flow {
        val roomList = id.map { id ->
            roomDataSource.getRoomDetail(id).data?.toRoom()
        }

        val notNullRoomList = roomList.filterNotNull()
        emit(notNullRoomList)
    }

    override fun getAllOrder(): Flow<List<Order>> = flow {
        val response = orderDataSource.getAllOrders()
        Timber.d("getAllOrder: ${response.message}")
        val orders = response.data?.data?.map {
            it.toOrder()
        }
        println(" getAllOrder: $orders")
        if (orders != null) {
            emit(orders)
        } else {
            emit(mutableListOf())
        }
    }

    override fun createOrder(order: Order): Flow<Order?> = flow {
        val roomCreateOrder = CreateOrderRoomRequest(
            order.startDate.parseDateZ(),
            order.endDate.parseDateZ(),
            order.people
        )
        val request = CreateOrderRequest(
            order.userId,
            order.price,
            order.noteBooking,
            roomCreateOrder
        )
        val response = orderDataSource.createOrder(order.roomId, request)

        if (response.message == null) {
            emit(response.data?.data?.toOrder())
        } else {
            Timber.e("createOrder: ${response.message}")
            emit(null)
        }
    }

    override fun payOrder(order: Order): Flow<Order?> = flow {
        Timber.d("Pay order: $order")
        val request = PaymentRequest(order.status.name, order.typePayment.name)
        val response = orderDataSource.payOrder(order.id, request)

        if (response.message == null) {
            emit(response.data?.data?.toOrder())
        } else {
            Timber.e("payOrder: ${response.message}")
            emit(null)
        }
    }

    override fun getOrderByUid(uid: String): Flow<List<Order>> = flow {
        val response = orderDataSource.getOrderByUid(uid)

        if (response.message == null) {
            val orders = response.data?.data?.map {
                it.toOrder()
            }
            emit(orders ?: emptyList())
        } else {
            Timber.e("getOrderByUid: ${response.message}")
            emit(emptyList())
        }
    }

    override fun getOrderDetail(id: String): Flow<Order?> = flow {
        val response = orderDataSource.getOrderDetail(id)

        if (response.message == null) {
            val order = response.data?.order?.toOrder()
            emit(order)
        } else {
            Timber.e("getOrderDetail: ${response.message}")
            emit(null)
        }
    }

    override fun createEvaluate(id: String, evaluate: RoomEvaluation): Flow<Boolean> = flow {
        val image = mutableListOf<Image>()
        evaluate.images.forEach {
            image.add(Image(it.url))
        }

        val request = CreateEvaluationRequest(evaluate.star, evaluate.content, evaluate.userId, image)
        val response = roomDataSource.createEvaluation(id, request)

        if (response.message == null) {
            emit(true)
        } else {
            Timber.e("createEvaluate: ${response.message}")
            emit(false)
        }
    }
}
