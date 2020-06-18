package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.concrete.magicdeck.R
import com.bootcamp.concrete.magicdeck.data.domain.Card
import com.bootcamp.concrete.magicdeck.data.domain.CardListHeader
import com.bootcamp.concrete.magicdeck.data.domain.CardListItem
import com.bootcamp.concrete.magicdeck.data.domain.Set
import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.ResultWrapper
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository
import kotlinx.coroutines.launch

class CatalogViewModel(
    private val typeRepository: TypeRepository,
    private val setRepository: SetRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

    private val types = ArrayList<String>()
    private val sets = ArrayList<Set>()
    private val cards = ArrayList<CardListItem>()

    private var typesIndex = 0
    private var setsIndex = 0
    private var pageNumber = 1
    private var allCardsRequested = false

    private val state = MutableLiveData<CatalogViewModelState>()
    private val loading = MutableLiveData<CatalogViewModelState>()

    fun getViewState(): LiveData<CatalogViewModelState> = state
    fun getLoading(): LiveData<CatalogViewModelState> = loading

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
        var itemsAmountAdded = list.size
        if (pageNumber == 1 && list.isNotEmpty()) {
            cards.add(CardListHeader(types[typesIndex]))
            itemsAmountAdded += 1
        }
        cards.addAll(list)
        return itemsAmountAdded
    }

    fun getInitialCards() {
        if (cards.isEmpty()) {
            loading.value = CatalogViewModelState.LoadingCards
            getTypes {
                getSets {
                    loading.value = CatalogViewModelState.DoneLoading
                    getCards()
                }
            }
        } else {
            state.value = CatalogViewModelState.ListCards(cards)
        }
    }

    private fun getTypes(funct: () -> Unit) {
        viewModelScope.launch {
            when (val response = typeRepository.listTypes()) {
                is ResultWrapper.Success -> {
                    types.addAll(response.value.sorted())
                    funct()
                }
                is ResultWrapper.GenericError -> {
                    state.value = CatalogViewModelState.Error(R.string.request_error)
                    loading.value = CatalogViewModelState.DoneLoading
                }
                is ResultWrapper.NetworkError -> {
                    state.value = CatalogViewModelState.Failure
                    loading.value = CatalogViewModelState.DoneLoading
                }
            }
        }
    }

    private fun getSets(funct: () -> Unit) {
        viewModelScope.launch {
            when (val response = setRepository.listSets()) {
                is ResultWrapper.Success -> {
                    if (response.value.isNotEmpty()) {
                        sets.addAll(response.value)
                        funct()
                    }
                }
                is ResultWrapper.NetworkError -> {
                    state.value = CatalogViewModelState.Error(R.string.request_error)
                    loading.value = CatalogViewModelState.DoneLoading
                }
                is ResultWrapper.GenericError -> {
                    state.value = CatalogViewModelState.Failure
                    loading.value = CatalogViewModelState.DoneLoading
                }
            }
        }
    }

    fun getCards() {
        if (!allCardsRequested && loading.value is CatalogViewModelState.DoneLoading) {
            loading.value = CatalogViewModelState.LoadingCards
            viewModelScope.launch {
                when (val response = cardRepository.listCards(
                    sets[setsIndex].code,
                    types[typesIndex],
                    pageNumber
                )) {
                    is ResultWrapper.Success -> {
                        onGetCardSuccess(response)
                    }
                    is ResultWrapper.NetworkError -> {
                        state.value = CatalogViewModelState.Error(R.string.request_error)
                        loading.value = CatalogViewModelState.DoneLoading
                    }
                    is ResultWrapper.GenericError -> {
                        state.value = CatalogViewModelState.Failure
                        loading.value = CatalogViewModelState.DoneLoading
                    }
                }

            }
        }
    }

    private fun onGetCardSuccess(response: ResultWrapper.Success<List<Card>>) {
        if (response.value.isEmpty()) {
            nextTypeOrSet()
            loading.value = CatalogViewModelState.DoneLoading
            getCards()
        } else {
            loading.value = CatalogViewModelState.DoneLoading
            val addedSize = addCards(response.value)
            if (response.value.size < 100) {
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
    }
}