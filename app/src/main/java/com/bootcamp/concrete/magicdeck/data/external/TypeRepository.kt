package com.bootcamp.concrete.magicdeck.data.external

import kotlinx.coroutines.Dispatchers.IO

class TypeRepository(
    private val retrofit: ApiService
) {
    private val netWorkHelper = NetWorkHelper(IO)

    suspend fun listTypes(): ResultWrapper<List<String>> {
        val response = netWorkHelper.safeApiCall(retrofit::listTypes)
        return when (response) {
            is ResultWrapper.Success -> {
                ResultWrapper.Success(
                    response.value.values.toList()[0]
                )
            }
            is ResultWrapper.NetworkError -> {
                response
            }
            is ResultWrapper.GenericError -> {
                response
            }
        }
    }

}