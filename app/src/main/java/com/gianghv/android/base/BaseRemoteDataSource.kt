package com.gianghv.android.base

import com.gianghv.android.util.app.AppConstants
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRemoteDataSource {

    suspend fun <T> safeCallApi(call: suspend () -> T): Response<T> {
        return try {
            val response = call.invoke()
            Response.Success(response)
        } catch (e: HttpException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: IOException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: Exception) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: SocketTimeoutException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        } catch (e: CancellationException) {
            Response.Error(e.message ?: AppConstants.DEFAULT_MESSAGE_ERROR)
        }
    }
}
