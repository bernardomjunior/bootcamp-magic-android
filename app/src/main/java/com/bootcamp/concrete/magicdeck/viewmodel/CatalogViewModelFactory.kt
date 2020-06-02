package com.bootcamp.concrete.magicdeck.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatalogViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatalogViewModel::class.java)) {
            return CatalogViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}