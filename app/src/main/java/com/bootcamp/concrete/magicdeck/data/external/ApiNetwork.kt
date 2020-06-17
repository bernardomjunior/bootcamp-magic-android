package com.bootcamp.concrete.magicdeck.data.external

import com.bootcamp.concrete.magicdeck.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiNetwork {

    private val okhttp3 by lazy {
        OkHttpClient()
            .newBuilder()
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    val retrofit: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttp3)
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
            .create(ApiService::class.java)
    }

}