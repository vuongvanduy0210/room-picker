package com.gianghv.android.views.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.OrderStatus
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomType
import com.gianghv.android.domain.TypePayment
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.room.RoomRepository
import com.gianghv.android.util.app.CostUtils
import com.gianghv.android.util.ext.dateFormatterDMYHM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.Date
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    val room = MutableLiveData<Room>()
    private val orderList = MutableLiveData<List<Order>>()

    val checkInDate = MutableLiveData("DD/MM/YYYY")
    val checkOutDate = MutableLiveData("DD/MM/YYYY")
    val checkInTime = MutableLiveData("HH:mm")
    val checkOutTime = MutableLiveData("HH:mm")
    val totalPrice = MutableLiveData(0)
    val peopleCount = MutableLiveData(1)

    val basePrice: MutableLiveData<Int> = MutableLiveData(0)
    val serviceFee: MutableLiveData<Int> = MutableLiveData(0)
    val tax: MutableLiveData<Int> = MutableLiveData(0)

    val note = MutableLiveData("")
    private var uid: String? = null

    val currentOrder = MutableLiveData<Order>()

    val paymentMethod = MutableLiveData(TypePayment.EMPTY)

    val toastMessage = MutableLiveData<String>()

    val orderSuccess = MutableLiveData(false)

    init {
        calculateTotalPrice()
        runBlocking {
            uid = authRepository.getTokenModel()?.uid.toString()
        }
    }

    fun requestRoom(id: String) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)

            val requestRoomFlow = roomRepository.requestRoom(id)
            val requestOrderListFlow = roomRepository.getAllOrder()

            combine(requestRoomFlow, requestOrderListFlow) { room, orderList ->
                Pair(room, orderList)
            }.collect { (room, orderList) ->
                this@OrderViewModel.room.value = room

                // fill order that match current room list
                val filteredOrderList = orderList.filter { room?.id == it.roomId }
                println(filteredOrderList)
                this@OrderViewModel.orderList.value = filteredOrderList.toMutableList()
            }
        }

        showLoading(false)
    }

    fun calculateTotalPrice() {
        Timber.d("Calculate Total Price. Room: ${room.value}")
        val checkinDate = "${checkInDate.value} ${checkInTime.value}".dateFormatterDMYHM()
        val checkoutDate = "${checkOutDate.value} ${checkOutTime.value}".dateFormatterDMYHM()

        Timber.d("Calculate Total Price $checkinDate $checkoutDate $room $peopleCount ")

        if (
            room.value != null && checkinDate != null &&
            checkoutDate != null && room.value != null &&
            peopleCount.value != null
        ) {
            var totalCost = 0

            val taxFreeCost = CostUtils.calculateTaxFreeCost(
                room.value!!.price,
                checkinDate,
                checkoutDate,
                room.value!!.type,
                peopleCount.value!!
            )
            totalCost += taxFreeCost
            basePrice.value = taxFreeCost

            if (room.value!!.type == RoomType.Vip) {
                val serviceFee = round(taxFreeCost * 0.05f).toInt()
                this.serviceFee.value = serviceFee
                totalCost += serviceFee
            }

            val taxCost = round(totalCost * 0.1f).toInt()
            tax.value = taxCost

            totalCost += taxCost
            totalPrice.value = totalCost
        } else {
            totalPrice.value = 0
        }
    }

    fun order() {
        if (canOrder()) {
            Timber.d("Can Order")
            val checkinDate = "${checkInDate.value} ${checkInTime.value}".dateFormatterDMYHM()
            val checkoutDate = "${checkOutDate.value} ${checkOutTime.value}".dateFormatterDMYHM()

            if (checkinDate != null && checkoutDate != null) {
                val order = Order(
                    "",
                    uid ?: "",
                    TypePayment.EMPTY,
                    totalPrice.value ?: 0,
                    OrderStatus.PENDING,
                    note.value ?: "",
                    room.value?.id ?: "",
                    peopleCount.value ?: 1,
                    checkinDate,
                    checkoutDate,
                    Date()
                )
                requestCreateOrder(order)
            }
        } else {
            Timber.d("Can't Order")
        }
    }

    private fun requestCreateOrder(order: Order) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            roomRepository.createOrder(order).transform { order ->
                Timber.d("Transform Order $order")
                if (order != null) {
                    emit(order)
                } else {
                    orderSuccess.value = false
                    toastMessage.value = "Order failed"
                }
            }.flatMapConcat { order ->
                Timber.d("FlatMap Order $order")
                order.typePayment = paymentMethod.value ?: TypePayment.EMPTY

                roomRepository.payOrder(order)
            }.collect {
                if (it != null) {
                    orderSuccess.value = true
                } else {
                    toastMessage.value = "Order failed"
                }
            }

            showLoading(false)
        }
    }

    private fun canOrder(): Boolean {
        val checkinDay = checkInDate.value
        val checkoutDay = checkOutDate.value
        val checkinTime = checkInTime.value
        val checkoutTime = checkOutTime.value

        val checkin = "$checkinDay $checkinTime"
        val checkout = "$checkoutDay $checkoutTime"

        if (!isDateFilled()) {
            toastMessage.value = "Please fill all date time fields"
            return false
        }

        if (!isRoomAvailableByStartEndDate(checkin, checkout)) {
            toastMessage.value = "Room is not available at this time"
            return false
        }

        if (room.value?.countPeople == null) {
            return false
        } else {
            if (peopleCount.value!! > room.value?.countPeople!!) {
                toastMessage.value = "Room has only ${room.value?.countPeople} people"
                return false
            }
        }

        return true
    }

    private fun isDateFilled(): Boolean {
        var flag = true
        if (checkInDate.value == "DD/MM/YYYY") {
            flag = flag and false
        }

        if (checkOutDate.value == "DD/MM/YYYY") {
            flag = flag and false
        }

        if (checkInTime.value == "HH:mm") {
            flag = flag and false
        }

        if (checkOutTime.value == "HH:mm") {
            flag and false
        }

        return flag
    }

    private fun isRoomAvailableByStartEndDate(checkinDay: String?, checkoutDay: String?): Boolean {
        var isNonOverlapping = true
        val checkin = checkinDay?.dateFormatterDMYHM()
        val checkout = checkoutDay?.dateFormatterDMYHM()

        Timber.d("checkin $checkin")
        Timber.d("checkout $checkout")

        if (checkin != null && checkout != null) {
            if (checkin.after(checkout)) {
                toastMessage.value = "Check in date must be before check out date"
            }
        }

        orderList.value?.forEach { order ->

            val startDate = order.startDate
            val endDate = order.endDate

            Timber.d("startDate $startDate")
            Timber.d("endDate $endDate")

            if (checkin != null && checkout != null) {
                if (dateRangesOverlap(startDate, endDate, checkin, checkout)) {
                    isNonOverlapping = isNonOverlapping and false
                }
            }
        }
        return isNonOverlapping
    }

    private fun dateRangesOverlap(startA: Date, endA: Date, startB: Date, endB: Date): Boolean {
        return startA.before(endB) && startB.before(endA) || startA == startB && endA == endB
    }
}
