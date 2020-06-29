package com.bootcamp.concrete.magicdeck.mocks

import com.bootcamp.concrete.magicdeck.data.domain.Set
import com.bootcamp.concrete.magicdeck.data.external.ResultWrapper
import com.bootcamp.concrete.magicdeck.extension.getJson

const val setFile = "sets.json"

fun successMockMapSet(classLoader: ClassLoader?): Map<String, List<Set>> {
    return classLoader?.getJson(setFile)
        ?: throw IllegalArgumentException("Null ClassLoader passed as parameter")
}

fun successMockListSet(classLoader: ClassLoader?): ResultWrapper<List<Set>> {
    val setMap = successMockMapSet(classLoader)
    return ResultWrapper.Success(setMap.values.toList()[0])
}