package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bootcamp.concrete.magicdeck.data.external.CardRepository
import com.bootcamp.concrete.magicdeck.data.external.SetRepository
import com.bootcamp.concrete.magicdeck.data.external.TypeRepository

class CatalogViewModel : ViewModel() {

    init {


    }

    lateinit var types : List<String>
//    lateinit var

    private val cardRepository = CardRepository


    private var page = 1
    private var setIndex = 0
    private var typeIndex = 0

    private val state = MutableLiveData<CatalogViewModelState>()

    fun getViewState() : LiveData<CatalogViewModelState> = state

    fun getNextCards() {

    }

}