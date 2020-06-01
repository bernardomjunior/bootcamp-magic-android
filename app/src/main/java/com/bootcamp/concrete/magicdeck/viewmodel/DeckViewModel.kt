package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card

class DeckViewModel : ViewModel() {

    private val _state = MutableLiveData<DeckViewModelState>()
    private val _response = MutableLiveData<DeckViewModelState.Response>()

    private val deck = ArrayList<Card>()

    val state: LiveData<DeckViewModelState>
        get() = _state

    val response: LiveData<DeckViewModelState.Response>
        get() = _response

    fun addCardToDeck(card: Card) {
        if (!isCardInDeck(card)) {
            deck.add(card)
            _response.value = DeckViewModelState.Response.CardAdded
        } else {
            _response.value = DeckViewModelState.Response.Error(R.string.card_already_inside_deck)
        }
    }

    fun removeCardFromDeck(card: Card) {
        if (isCardInDeck(card)) {
            deck.remove(card)
        }
        _response.value = DeckViewModelState.Response.CardRemoved
    }

    private fun isCardInDeck(card: Card): Boolean {
        val list = deck.filter { cardIntoDeck -> card.id == cardIntoDeck.id }
        return list.size > 0
    }

    fun checkCard(card: Card) {
        if (isCardInDeck(card)) {
            _state.value = DeckViewModelState.CardInDeck
        } else {
            _state.value = DeckViewModelState.CardNotInDeck
        }
    }

}