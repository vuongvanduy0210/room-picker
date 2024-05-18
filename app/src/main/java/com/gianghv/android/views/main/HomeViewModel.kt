package com.gianghv.android.views.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.Room
import com.gianghv.android.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val roomList = MutableLiveData<MutableList<Room>>()
    val orderList = MutableLiveData<MutableList<Order>>()

    val _checkInDate = MutableLiveData<String>()
    val _checkOutDate = MutableLiveData<String>()
    val _peopleCount = MutableLiveData(1)
    val _minPrice = MutableLiveData<Int>()
    val _maxPrice = MutableLiveData<Int>()

    val checkInDate: String get() = _checkInDate.value.toString()
    val checkOutDate: String get() = _checkOutDate.value.toString()
    val peopleCount: Int get() = _peopleCount.value ?: 1
    val minPrice: Int get() = _minPrice.value ?: 500000
    val maxPrice: Int get() = _maxPrice.value ?: 10000000

    fun setCheckInDate(date: String) {
        _checkInDate.value = date
    }

    fun setCheckOutDate(date: String) {
        _checkOutDate.value = date
    }

    fun setPeopleCount(count: Int) {
        _peopleCount.value = count
    }

    fun setMinPrice(price: Int) {
        _minPrice.value = price
    }

    fun setMaxPrice(price: Int) {
        _maxPrice.value = price
    }

    fun clearFilter() {
        _checkInDate.value = ""
        _checkOutDate.value = ""
        _peopleCount.value = 1
        _minPrice.value = 500000
        _maxPrice.value = 10000000
    }

    fun increasePeopleCount() {
        val count = _peopleCount.value ?: 1
        _peopleCount.value = count + 1
    }

    fun decreasePeopleCount() {
        val count = _peopleCount.value ?: 1
        if (count > 1) {
            _peopleCount.value = count - 1
        }
    }

    fun clearPeopleCount() {
        _peopleCount.value = 1
    }

    fun requestRooms() {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)

            val roomListFlow = roomRepository.requestAllRoom().flatMapConcat { room ->
                val ids = room.map { it.id }
                roomRepository.getDetailRoomList(ids)
            }

            val orderListFlow = roomRepository.getAllOrder()

            combine(roomListFlow, orderListFlow) { roomList, orderList ->
                Pair(roomList, orderList)
            }.collect { (roomList, orderList) ->
                this@HomeViewModel.roomList.value = roomList.toMutableList()

                // fill order that match current room list
                val filteredOrderList = orderList.filter { roomList.any { room -> room.id == it.roomId } }
                println(filteredOrderList)
                this@HomeViewModel.orderList.value = filteredOrderList.toMutableList()
            }
        }

        showLoading(false)
    }
}
