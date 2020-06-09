package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.Set
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sets/")
    suspend fun listSets(): Map<String, List<Set>>

    @GET("types/")
    suspend fun listTypes(): Map<String, List<String>>

    @GET("cards")
    suspend fun listCards(
        @Query("set") set: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Map<String, List<Card>>

}