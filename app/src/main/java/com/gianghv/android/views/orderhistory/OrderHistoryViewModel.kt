package com.gianghv.android.views.orderhistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.Room
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    private var uid: String? = null
    val orders: MutableLiveData<MutableList<Order>> = MutableLiveData()
    val rooms: MutableLiveData<MutableList<Room>> = MutableLiveData()

    init {
        runBlocking {
            uid = authRepository.getTokenModel()?.uid
        }
    }

    fun getOrders() {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            val getOrderFlow = roomRepository.getOrderByUid(uid.toString())
            val getRoomFlow = roomRepository.requestAllRoom()

            combine(getOrderFlow, getRoomFlow) { orderList, roomList ->
                Pair(roomList, orderList)
            }.collect {
                rooms.value = it.first.toMutableList()
                orders.value = it.second.toMutableList()
            }

            showLoading(false)
        }
    }
}
