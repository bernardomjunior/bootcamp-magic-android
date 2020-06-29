package com.bootcamp.concrete.magicdeck.mocks

import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.external.ResultWrapper
import com.bootcamp.concrete.magicdeck.extension.getJson

const val cardFile = "generic_cards.json"

fun successMockMapCard(classLoader: ClassLoader?): Map<String, List<Card>> {
    return classLoader?.getJson(cardFile)
        ?: throw IllegalArgumentException("Null ClassLoader passed as parameter")
}

fun successMockListCard(classLoader: ClassLoader?): ResultWrapper<List<Card>> {
    val cardMap = successMockMapCard(classLoader)
    return ResultWrapper.Success(cardMap.values.toList()[0])
}


