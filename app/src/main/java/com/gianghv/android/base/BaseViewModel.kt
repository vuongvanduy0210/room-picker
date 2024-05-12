package com.gianghv.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gianghv.android.domain.BGType
import com.gianghv.android.domain.ResponseMessage
import com.gianghv.android.util.app.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    var job: Job? = null

    var exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // show message
    }

    val responseMessage: MutableSharedFlow<ResponseMessage> by lazy { MutableSharedFlow() }
    val isLoading: MutableSharedFlow<Boolean> by lazy { MutableSharedFlow() }

    fun <T> handleResponse(response: Response<T>, onSuccess: (T?) -> Unit, onError: (String?) -> Unit) {
        when (response) {
            is Response.Success -> {
                onSuccess.invoke(response.data)
            }

            is Response.Error -> {
                viewModelScope.launch {
                    showMessage(
                        message = response.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
                        bgType = BGType.BG_TYPE_ERROR
                    )
                }
                onError.invoke(response.message)
            }
        }
    }

    suspend fun showMessage(message: String, bgType: BGType) {
        responseMessage.emit(
            ResponseMessage(
                message = message,
                bgType = bgType
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }
}
