package com.wbconcept.myapplication.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wbconcept.myapplication.Repositories.FavoriteRepository
import com.wbconcept.myapplication.Repositories.ProductsRepository
import com.wbconcept.myapplication.Repositories.SessionRepository

class FavoriteViewModelProviderFacotry (val faoriteRepository: FavoriteRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(faoriteRepository) as T
    }
}