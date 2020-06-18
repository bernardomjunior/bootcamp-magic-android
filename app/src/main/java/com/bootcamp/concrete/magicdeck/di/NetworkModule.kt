package com.bootcamp.concrete.magicdeck.di

import com.bootcamp.concrete.magicdeck.BuildConfig
import com.bootcamp.concrete.magicdeck.data.external.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        loggingInterceptor
    }
    single {
        OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
    }
    single {
        val retrofit: Retrofit = get()
        retrofit
            .create(ApiService::class.java)
    }
}