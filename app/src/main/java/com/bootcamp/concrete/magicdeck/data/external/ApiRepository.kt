package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.BuildConfig.BASE_URL
import com.bootcamp.concrete.magicdeck.data.domain.Set
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiRepository {

    private val okhttp3 = OkHttpClient()
        .newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okhttp3)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().create()
            )
        )
        .build()
        .create(ApiService::class.java)

    fun listSets(
        onSuccess: (List<Set>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit
    ) {
        retrofit.listSets().enqueue(object : Callback<Map<String, List<Set>>> {
            override fun onResponse(
                call: Call<Map<String, List<Set>>>,
                response: Response<Map<String, List<Set>>>
            ) {
                if (response.isSuccessful) {
                    val dict = response.body() as Map<String, List<Set>>
                    dict["sets"]?.let(onSuccess)
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Map<String, List<Set>>>, t: Throwable) {
                onFailure()
            }
        })
    }

    fun listTypes(
        onSuccess: (List<String>) -> Unit,
        onError: () -> Unit,
        onFailure: () -> Unit
    ) {
        retrofit.listTypes().enqueue(object : Callback<Map<String, List<String>>> {
            override fun onResponse(
                call: Call<Map<String, List<String>>>,
                response: Response<Map<String, List<String>>>
            ) {
                if (response.isSuccessful) {
                    val dict = response.body() as Map<String, List<String>>
                    dict["types"]?.let(onSuccess)

                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Map<String, List<String>>>, t: Throwable) {
                onFailure()
            }
        })
    }
}