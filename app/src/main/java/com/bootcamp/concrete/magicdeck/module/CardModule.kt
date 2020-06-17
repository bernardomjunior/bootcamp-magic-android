package com.bootcamp.concrete.magicdeck.module

import com.bootcamp.concrete.magicdeck.data.external.ApiNetwork
import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository
import org.koin.dsl.module

val cardModule = module {
    single { ApiNetwork().retrofit }
    single { TypeRepository() }
    single { CardRepository() }
    single { SetRepository() }
}