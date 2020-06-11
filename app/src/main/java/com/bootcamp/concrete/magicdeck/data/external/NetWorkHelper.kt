package com.bootcamp.concrete.magicdeck.data.external

import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NetWorkHelper(private val dispatcher: CoroutineDispatcher) {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                catchErrors<T>(throwable)
            }
        }
    }

    private fun <T> catchErrors(throwable: Throwable): ResultWrapper<T> {
        return when (throwable) {
            is IOException -> ResultWrapper.NetworkError
            is HttpException -> {
                ResultWrapper.GenericError(
                    code = throwable.code(),
                    error = convertErrorBody(throwable)
                )
            }
            else -> ResultWrapper.GenericError(null, null)
        }
    }

    private fun convertErrorBody(throwable: HttpException): String? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(String::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}