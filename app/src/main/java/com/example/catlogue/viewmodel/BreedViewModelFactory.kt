package com.example.catlogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.data.local.FavoriteDao
/*
 * Criei essa factory porque o BreedViewModel precisa de parâmetros no construtor
 * (repository e favoriteDao). O Android não sabe criar ViewModels assim automaticamente,
 * então usei essa factory para "ensinar" como criar o BreedViewModel com esses dados.
 */

class BreedViewModelFactory(
    private val repository: BreedRepository,
    private val favoriteDao: FavoriteDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BreedViewModel(repository, favoriteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
