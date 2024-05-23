package com.gianghv.android.repository.user

import com.gianghv.android.datasource.remote.UserDataSource
import com.gianghv.android.domain.User
import com.gianghv.android.util.ext.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

interface UserRepository {
    fun requestUserDetail(id: String): Flow<User?>
    suspend fun updateUser(user: User): Flow<User?>
}

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override fun requestUserDetail(id: String): Flow<User?> = flow {
        val response = userDataSource.getUserDetail(id)

        if (response.message != null) {
            emit(null)
            Timber.e("message: ${response.message}")
        } else {
            emit(response.data?.user?.toUser())
        }
    }
    override suspend fun updateUser(user: User): Flow<User?> = flow {
        val response = userDataSource.updateUser(user.id, user.name, user.email, user.role)

        if (response.message != null) {
            emit(null)
            Timber.e(response.message)
        } else {
            val data = response.data?.user?.toUser()
            emit(data)
        }
    }

}
