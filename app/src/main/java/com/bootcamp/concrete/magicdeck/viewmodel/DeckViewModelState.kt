package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.annotation.StringRes
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem

sealed class DeckViewModelState {
    sealed class Response {
        object CardAdded : DeckViewModelState.Response()
        object CardRemoved : DeckViewModelState.Response()
        class Error(@StringRes val messageId: Int) : DeckViewModelState.Response()
    }

    object CardInDeck : DeckViewModelState()
    object CardNotInDeck : DeckViewModelState()
    sealed class Deck {
        class List(val items: ArrayList<CardListItem>) : DeckViewModelState.Deck()
        class ListSize(val size: Int) : DeckViewModelState.Deck()
    }
}