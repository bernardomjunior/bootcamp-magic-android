package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Set
import kotlinx.coroutines.Dispatchers.IO

class SetRepository(
    private val retrofit: ApiService
) {
    private val netWorkHelper = NetWorkHelper(IO)

    suspend fun listSets(): ResultWrapper<List<Set>> {
        val response = netWorkHelper.safeApiCall(retrofit::listSets)
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