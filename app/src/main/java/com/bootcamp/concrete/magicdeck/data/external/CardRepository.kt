package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Card
import kotlinx.coroutines.Dispatchers.IO

class CardRepository(
    private val retrofit: ApiService
) {
    private val CARDS = "cards"

    suspend fun listCards(
        set: String,
        type: String,
        page: Int
    ): ResultWrapper<List<Card>> {
        val response = netWorkHelper.safeApiCall(encapsulateListCard(set, type, page))
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

    private fun encapsulateListCard(
        set: String,
        type: String,
        page: Int
    ): suspend () -> Map<String, List<Card>> {
        return suspend {
            retrofit.listCards(set, type, page)
        }
    }

}