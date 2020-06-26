package com.bootcamp.concrete.magicdeck.extension

import com.squareup.moshi.Moshi

inline fun <reified T> ClassLoader.getJson(path: String): T {
    val text = this.getResource(path)?.readText()
    val moshi = Moshi.Builder().build()
    return text?.let { moshi.adapter(T::class.java).fromJson(it) }
        ?: throw IllegalArgumentException("Json inválido")
}