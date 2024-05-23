package com.gianghv.android.views.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Room
import com.gianghv.android.domain.User
import com.gianghv.android.repository.room.RoomRepository
import com.gianghv.android.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    val roomLiveData = MutableLiveData<Room>()
    val users = MutableLiveData<MutableList<User>>()

    fun requestRoom(id: String) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)

            roomRepository.requestRoom(id).collect {
                roomLiveData.value = it
                requestUsers()
            }
        }

        showLoading(false)
    }

    private fun requestUsers() {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)

            if (!roomLiveData.value?.evaluation.isNullOrEmpty()) {
                Timber.d("requestUsers: ${roomLiveData.value?.evaluation}")
                val listFlow = roomLiveData.value?.evaluation?.asFlow()

                listFlow?.flatMapMerge(concurrency = 5) { re ->
                    userRepository.requestUserDetail(re.userId)
                }?.collect {
                    if (it != null) {
                        users.addItem(it)
                    }
                }
            }

            showLoading(false)
        }
    }

    fun <T> MutableLiveData<MutableList<T>>.addItem(item: T) {
        val value = this.value ?: arrayListOf()
        value.add(item)
        this.value = value
    }
}
