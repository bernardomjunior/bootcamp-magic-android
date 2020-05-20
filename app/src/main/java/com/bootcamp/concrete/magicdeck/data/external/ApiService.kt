package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.data.domain.Set
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("sets/")
    fun listSets(): Call<Map<String, List<Set>>>

    @GET("types/")
    fun listTypes(): Call<Map<String, List<String>>>

}