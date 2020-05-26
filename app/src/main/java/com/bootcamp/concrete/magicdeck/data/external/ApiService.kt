package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.Set
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("sets/")
    fun listSets(): Call<Map<String, List<Set>>>

    @GET("types/")
    fun listTypes(): Call<Map<String, List<String>>>

    @GET("cards")
    fun listCards(
        @Query("set") set: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Call<Map<String,List<Card>>>

}