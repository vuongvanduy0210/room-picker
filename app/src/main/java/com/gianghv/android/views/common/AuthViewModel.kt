package com.gianghv.android.views.common

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
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun signIn(email: String, password: String) {
        job = viewModelScope.launch(exceptionHandler) {
            showLoading(true)
            val response = authRepository.signIn(
                LoginRequest(email = email, password = password)
            )
            showLoading(false)

            handleResponse(
                response = response,
                onSuccess = {
                    handleMessage(
                        message = it?.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
                        bgType = if (it != null) BGType.BG_TYPE_SUCCESS else BGType.BG_TYPE_ERROR
                    )
                },
                onError = {
                    val message =
                        if (it?.contains("400") == true) {
                            AppConstants.EMAIL_PASSWORD_MESSAGE_ERROR
                        } else {
                            AppConstants.DEFAULT_MESSAGE_ERROR
                        }
                    handleMessage(
                        message = message,
                        bgType = BGType.BG_TYPE_ERROR
                    )
                }
            )
        }
    }

    fun signUp(email: String, name: String, password: String) {
        job = viewModelScope.launch {
            showLoading(true)
            val response = authRepository.signUp(
                SignUpRequest(email = email, name = name, password = password, confirmPassword = password)
            )
            showLoading(false)

            handleResponse(
                response = response,
                onSuccess = {
                    handleMessage(
                        message = it?.message ?: AppConstants.DEFAULT_MESSAGE_ERROR,
                        bgType = if (it != null) BGType.BG_TYPE_SUCCESS else BGType.BG_TYPE_ERROR
                    )
                },
                onError = {
                    val message =
                        if (it?.contains("400") == true) {
                            AppConstants.SIGN_UP_MESSAGE_ERROR
                        } else {
                            AppConstants.DEFAULT_MESSAGE_ERROR
                        }
                    handleMessage(
                        message = message,
                        bgType = BGType.BG_TYPE_ERROR
                    )
                }
            )
        }
    }
}
