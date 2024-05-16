package com.gianghv.android.views.common

import androidx.lifecycle.viewModelScope
import com.gianghv.android.base.BaseViewModel
import com.gianghv.android.domain.TokenModel
import com.gianghv.android.network.model.login.LoginRequest
import com.gianghv.android.network.model.signup.SignUpRequest
import com.gianghv.android.repository.auth.AuthRepository
import com.gianghv.android.util.app.AppConstants
import com.gianghv.android.util.ext.toUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _isSignedIn = MutableSharedFlow<Boolean>()
    val isSignedIn: SharedFlow<Boolean> get() = _isSignedIn

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
                    // save access token to local
                    it?.toUser()?.let { user ->
                        authRepository.saveTokenModel(
                            TokenModel(accessToken = it.accessToken!!, uid = user.id)
                        )
                        _isSignedIn.emit(true)
                    }
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
                    it?.toUser()?.let { user ->
                        authRepository.saveTokenModel(
                            TokenModel(accessToken = it.accessToken!!, uid = user.id)
                        )
                        _isSignedIn.emit(true)
                    }
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

    fun getAccessToken() {
        job = viewModelScope.launch {
            showLoading(true)
            val tokenModel = authRepository.getTokenModel()
            showLoading(false)

            handleMessage(
                message = if (tokenModel != null) "Đăng nhập thành công" else "Không có tài khoản",
                bgType = if (tokenModel != null) BGType.BG_TYPE_SUCCESS else BGType.BG_TYPE_ERROR
            )
            _isSignedIn.emit(tokenModel != null)
        }
    }
}
