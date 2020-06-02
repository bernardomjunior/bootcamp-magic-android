package com.bootcamp.concrete.magicdeck.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DeckViewModelFactory (val application: Application) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeckViewModel::class.java)) {
            return DeckViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}