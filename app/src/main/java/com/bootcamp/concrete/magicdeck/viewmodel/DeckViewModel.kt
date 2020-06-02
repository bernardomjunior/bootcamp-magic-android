package com.bootcamp.concrete.magicdeck.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bootcamp.concrete.magicdeck.BuildConfig
import androidx.room.Room
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListHeader
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.data.local.CardDatabase

class DeckViewModel(application: Application) : AndroidViewModel(application) {

    private val _state = MutableLiveData<DeckViewModelState>()
    private val _response = MutableLiveData<DeckViewModelState.Response>()
    private val _favorites = MutableLiveData<DeckViewModelState.Deck>()

    val state: LiveData<DeckViewModelState>
        get() = _state

    val response: LiveData<DeckViewModelState.Response>
        get() = _response

    val favorites: LiveData<DeckViewModelState.Deck>
        get() = _favorites

    private val deck = ArrayList<Card>()

    private val db = Room.databaseBuilder(
        getApplication(),
        CardDatabase::class.java,
        BuildConfig.DATABASE_NAME
    ).build()

    fun addCardToDeck(card: Card) {
        if (!isCardInDeck(card)) {
            deck.add(card)
            db.cardDao().addCard(card)
            _response.value = DeckViewModelState.Response.CardAdded
        } else {
            _response.value = DeckViewModelState.Response.Error(R.string.card_already_inside_deck)
        }
    }

    fun removeCardFromDeck(card: Card) {
        if (isCardInDeck(card)) {
            deck.remove(card)
            db.cardDao().removeCard(card)
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

    fun getFavorites() {
        val responseList = ArrayList<CardListItem>()
        val types = ArrayList<String>()
        deck.clear()
        deck.addAll(db.cardDao().getAll())
        deck.map { card -> types.addAll(card.types) }
        for (type in types.distinct().sorted()) {
            responseList.add(CardListHeader(type))
            responseList.addAll(deck.filter { card -> type in card.types })
        }
        _favorites.value = DeckViewModelState.Deck.List(responseList)
    }

    fun getCardsAmount() {
        _favorites.value = DeckViewModelState.Deck.ListSize(deck.size)
    }

}