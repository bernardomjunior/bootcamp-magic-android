package com.bootcamp.concrete.magicdeck.data.external

sealed class ResultWrapper<out T> {
    class Success<out T>(val value: T) : ResultWrapper<T>()
    class GenericError(val code: Int?, val error: String?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}