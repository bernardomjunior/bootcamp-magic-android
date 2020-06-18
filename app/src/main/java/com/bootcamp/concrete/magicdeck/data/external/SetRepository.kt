package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Set

class SetRepository(
    private val retrofit: ApiService,
    private val netWorkHelper: NetWorkHelper
) {
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