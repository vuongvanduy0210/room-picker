package com.gianghv.android.views.signin

import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.BGType
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.util.app.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun login(email: String, password: String) {
        job = viewModelScope.launch(exceptionHandler) {
            isLoading.emit(true)
            val response = authRepository.login(
                LoginRequest(email = email, password = password)
            )
            isLoading.emit(false)

            handleResponse(
                response = response,
                onSuccess = {
                    viewModelScope.launch {
                        showMessage(
                            message = it?.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
                            bgType = if (it != null) BGType.BG_TYPE_SUCCESS else BGType.BG_TYPE_ERROR
                        )
                        // save user to local app
                    }
                },
                onError = {}
            )
        }
    }

    fun signUp(email: String, name: String, password: String) {
        job = viewModelScope.launch {
            isLoading.emit(true)
            val response = authRepository.signUp(
                SignUpRequest(email = email, name = name, password = password, confirmPassword = password)
            )
            isLoading.emit(false)

            handleResponse(
                response = response,
                onSuccess = {
                    viewModelScope.launch {
                        showMessage(
                            message = it?.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
                            bgType = if (it != null) BGType.BG_TYPE_SUCCESS else BGType.BG_TYPE_ERROR
                        )
                        // save user to local app
                    }
                },
                onError = {}
            )
        }
    }
}
