package com.wbconcept.myapplication.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Cart_Object
import com.wbconcept.myapplication.Repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
private val cartRepository: CartRepository
) : ViewModel() {
    val productList: LiveData<List<Cart_Object>> =cartRepository.get_all_productsDB()

    fun saveArticle(article: Cart_Item) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.insert(article)
    }
    fun update(article: Cart_Item) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.update(article)
    }

    fun delete(article: Cart_Item) = viewModelScope.launch(Dispatchers.IO) {
        cartRepository.delete(article)
    }
}