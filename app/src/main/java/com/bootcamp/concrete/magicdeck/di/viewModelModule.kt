package com.bootcamp.concrete.magicdeck.di

import com.bootcamp.concrete.magicdeck.viewmodel.CatalogViewModel
import com.bootcamp.concrete.magicdeck.viewmodel.DeckViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CatalogViewModel(get(), get(), get()) }
    viewModel { DeckViewModel() }
}