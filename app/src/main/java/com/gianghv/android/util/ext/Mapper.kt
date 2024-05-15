package com.gianghv.android.util.ext

import com.gianghv.android.domain.User
import com.gianghv.android.network.model.login.LoginResponse
import com.gianghv.android.network.model.signup.SignUpResponse

object Mapper {

    fun LoginResponse.toUser() = User(
        id = user?.id ?: "",
        name = user?.name ?: "",
        email = user?.email ?: "",
        role = user?.role ?: ""
    )

    fun SignUpResponse.toUser() = User(
        id = user?.id ?: "",
        name = user?.name ?: "",
        email = user?.email ?: "",
        role = user?.role ?: ""
    )
}
