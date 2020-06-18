package com.bootcamp.concrete.magicdeck.di

import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { TypeRepository(get(), get()) }
    single { CardRepository(get(), get()) }
    single { SetRepository(get(), get()) }
}