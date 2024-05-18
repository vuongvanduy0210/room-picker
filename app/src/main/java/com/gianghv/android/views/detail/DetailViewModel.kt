package com.gianghv.android.views.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.Room
import com.gianghv.android.repository.room.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    val roomLiveData = MutableLiveData<Room>()

    fun requestRoom(id: String) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)

            roomRepository.requestRoom(id).collect {
                roomLiveData.value = it
            }
        }

        showLoading(false)
    }
}
