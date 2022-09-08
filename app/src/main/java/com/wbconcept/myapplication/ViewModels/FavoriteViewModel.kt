package com.wbconcept.myapplication.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.Repositories.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(
    val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val dbProducts: LiveData<List<Product_ListItem>>


    init {
        dbProducts = favoriteRepository.get_all_favoritesDB()
    }


    fun saveArticle(article: Int) =    viewModelScope.launch(Dispatchers.IO) {

        favoriteRepository.saveFavorite(article)
    }

    fun delete(article: Int) =    viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.deleteFavorite(article)
    }
}