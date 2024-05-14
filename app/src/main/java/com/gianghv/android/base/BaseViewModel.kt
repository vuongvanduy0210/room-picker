package com.gianghv.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianghv.android.domain.BGType
import com.gianghv.android.domain.ResponseMessage
import com.gianghv.android.util.app.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    var job: Job? = null

    var exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // show message
        handleMessage(
            message = throwable.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
            bgType = BGType.BG_TYPE_ERROR
        )
    }

    private val _responseMessage = MutableSharedFlow<ResponseMessage>()
    val responseMessage: SharedFlow<ResponseMessage> = _responseMessage

    private val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading: SharedFlow<Boolean> = _isLoading

    fun <T> handleResponse(response: Response<T>, onSuccess: (T?) -> Unit, onError: (String?) -> Unit) {
        when (response) {
            is Response.Success -> {
                onSuccess.invoke(response.data)
            }

            is Response.Error -> {
                onError.invoke(response.message)
            }
        }
    }

    fun handleMessage(message: String, bgType: BGType) {
        viewModelScope.launch {
            _responseMessage.emit(
                ResponseMessage(
                    message = message,
                    bgType = bgType
                )
            )
        }
    }

    fun showLoading(isShow: Boolean) {
        viewModelScope.launch {
            _isLoading.emit(isShow)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}
