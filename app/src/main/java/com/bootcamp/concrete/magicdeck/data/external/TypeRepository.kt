package com.bootcamp.concrete.magicdeck.data.external

class TypeRepository(
    private val retrofit: ApiService,
    private val netWorkHelper: NetWorkHelper
) {
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