package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListHeader
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.data.domain.Set
import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository

class CatalogViewModel : ViewModel() {

    private val types = ArrayList<String>()
    private val sets = ArrayList<Set>()
    val cards = ArrayList<CardListItem>()
    private var typesIndex = 0
    private var setsIndex = 0
    private var pageNumber = 1
    private var allCardsRequested = false

    private val state = MutableLiveData<CatalogViewModelState>()

    fun getViewState(): LiveData<CatalogViewModelState> = state

    fun getInitialCards() {
        if (cards.isEmpty()) {
            getTypes {
                getSets {
                    getCards()
                }
            }
        } else {
            state.value = CatalogViewModelState.ListCards(cards)
        }
    }

    private fun getTypes(funct: () -> Unit) {
        TypeRepository.listTypes(
            {
                types.addAll(it)
                funct()
            },
            { state.value = CatalogViewModelState.Error(R.string.request_error) },
            { state.value = CatalogViewModelState.Failure })
    }

    private fun getSets(funct: () -> Unit) {
        SetRepository.listSets(
            {
                sets.addAll(it)
                funct()
            },
            { state.value = CatalogViewModelState.Error(R.string.request_error) },
            { state.value = CatalogViewModelState.Failure })
    }

    fun getCards() {
        if (!allCardsRequested) {
            CardRepository.listCards(
                sets[setsIndex].code,
                types[typesIndex],
                pageNumber,
                {
                    if (it.isEmpty()) {
                        nextTypeOrSet()
                        getCards()
                    } else {
                        val addedSize = addCards(it)
                        if (it.size < 100) {
                            nextTypeOrSet()
                        } else {
                            nextPage()
                        }

                        state.value = CatalogViewModelState.ListCards(
                            cards.subList(
                                cards.size - addedSize,
                                cards.size - 1
                            )
                        )
                    }
                },
                { state.value = CatalogViewModelState.Error(R.string.request_error) },
                { state.value = CatalogViewModelState.Failure }
            )
        }
    }

    private fun nextPage() {
        pageNumber += 1
    }

    private fun nextTypeOrSet() {
        pageNumber = 1
        if (types.size > typesIndex + 1) {
            typesIndex += 1
        } else {
            if (sets.size > setsIndex + 1) {
                setsIndex += 1
                typesIndex = 0
            } else {
                allCardsRequested = false
            }
        }
    }

    private fun addCards(list: List<Card>): Int {
        var itemsAdded = list.size
        if (pageNumber == 1 && list.isNotEmpty()) {
            cards.add(CardListHeader(types[typesIndex]))
            itemsAdded += 1
        }
        cards.addAll(list)
        return itemsAdded
    }

}