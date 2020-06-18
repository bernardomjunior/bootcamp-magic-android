package com.bootcamp.concrete.magicdeck.di

import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TypeRepository(get()) }
    single { CardRepository(get()) }
    single { SetRepository(get()) }
}