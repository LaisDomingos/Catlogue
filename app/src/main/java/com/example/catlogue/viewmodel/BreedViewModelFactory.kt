package com.example.catlogue.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.catlogue.data.repository.BreedRepository
import com.example.catlogue.data.local.FavoriteDao
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
