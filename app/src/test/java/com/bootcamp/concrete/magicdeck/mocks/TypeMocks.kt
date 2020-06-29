package com.bootcamp.concrete.magicdeck.mocks

import com.bootcamp.concrete.magicdeck.data.external.ResultWrapper
import com.bootcamp.concrete.magicdeck.extension.getJson

const val typeFile = "types.json"

fun successMockMapTypes(classLoader: ClassLoader?): Map<String, List<String>> {
    return classLoader?.getJson(typeFile) ?: throw IllegalArgumentException("Null ClassLoader passed as parameter")
}

fun successMockListTypes(classLoader: ClassLoader?): ResultWrapper<List<String>> {
    val typesMap = successMockMapTypes(classLoader)
    return ResultWrapper.Success(typesMap.values.toList()[0])
}