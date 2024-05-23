package com.gianghv.android.views.orderdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Image
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.RoomEvaluation
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    var _orderDetail = MutableLiveData<Order>()
    val room = MutableLiveData<Room>()
    val isEvaluate = MutableLiveData<Boolean?>(null)

    val orderDetail get() = _orderDetail.value
    private var uid: String? = null
    val imageUrls: MutableLiveData<MutableList<String>> = MutableLiveData()

    init {
        runBlocking {
            uid = authRepository.getTokenModel()?.uid
        }
    }

    fun getOrderDetail(orderId: String) {
        showLoading(true)
        job = viewModelScope.launch(exceptionHandler) {
            roomRepository.getOrderDetail(orderId).filterNotNull().flatMapConcat {
                _orderDetail.postValue(it)
                roomRepository.requestRoom(it.roomId)
            }.collect {
                room.postValue(it)
                showLoading(false)
            }
        }
    }

    fun evaluate(rate: Double, comment: String, imageUrls: MutableList<String>) {
        showLoading(true)
        job = viewModelScope.launch(exceptionHandler) {
            val images = mutableListOf<Image>()
            Timber.d("imageUrls: $imageUrls")
            imageUrls.forEach {
                images.add(Image("", it))
            }
            val roomEvaluate = RoomEvaluation("", uid.toString(), comment, rate, images)
            roomRepository.createEvaluate(_orderDetail.value?.roomId.toString(), roomEvaluate).collect {
                isEvaluate.value = it
            }
            showLoading(false)
        }
    }
}
