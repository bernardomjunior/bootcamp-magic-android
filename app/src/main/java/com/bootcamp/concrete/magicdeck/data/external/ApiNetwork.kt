package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiNetwork {

    private val okhttp3 by lazy {  OkHttpClient()
        .newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
    }

    val retrofit: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttp3)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            .build()
            .create(ApiService::class.java)
    }

}