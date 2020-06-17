package com.bootcamp.concrete.magicdeck.module

import com.bootcamp.concrete.magicdeck.data.external.ApiNetwork
import org.koin.dsl.module

val cardModule = module {
    single { ApiNetwork().retrofit }
}