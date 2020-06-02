package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.annotation.StringRes
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem

sealed class CatalogViewModelState {
    class Error(@StringRes val stringId: Int) : CatalogViewModelState()
    object Failure : CatalogViewModelState()
    class ListCards(val cardListItems: List<CardListItem>) : CatalogViewModelState()
    object LoadingCards : CatalogViewModelState()
    object DoneLoading : CatalogViewModelState()
}