package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.annotation.StringRes

sealed class DeckViewModelState {
    sealed class Response{
        object CardAdded: DeckViewModelState.Response()
        object CardRemoved: DeckViewModelState.Response()
        class Error(@StringRes val messageId: Int) : DeckViewModelState.Response()
    }
    object CardInDeck: DeckViewModelState()
    object CardNotInDeck: DeckViewModelState()
}