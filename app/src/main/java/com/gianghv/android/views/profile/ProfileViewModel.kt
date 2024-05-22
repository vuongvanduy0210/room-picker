package com.gianghv.android.views.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Order
import com.gianghv.android.domain.User
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.repository.room.RoomRepository
import com.gianghv.android.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val roomRepository: RoomRepository,
    private val userRepo: UserRepository
) : BaseViewModel() {
    val user = MutableLiveData<User>()
    val order = MutableLiveData<List<Order>>()

    var uid: String = ""

    init {
        runBlocking {
            uid = authRepository.getTokenModel()?.uid.toString()
        }

        getUser()
    }

    fun logout() {
        runBlocking { authRepository.clearTokenModel() }
    }

    private fun getUser() {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            val orderFlow = roomRepository.getAllOrder()
            val userFlow = userRepo.requestUserDetail(uid)

            combine(orderFlow, userFlow) { it1, it2 ->
                Pair(it1, it2)
            }.collect {
                order.value = it.first
                user.value = it.second
                showLoading(false)
            }
        }
    }

    fun updateUser(user: User) {
        job = viewModelScope.launch(exceptionHandler) {
            userRepo.updateUser(user).collect{
                getUser()
            }
        }
    }

}
