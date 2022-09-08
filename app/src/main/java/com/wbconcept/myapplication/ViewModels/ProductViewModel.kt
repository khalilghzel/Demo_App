package com.wbconcept.myapplication.ViewModels

import android.util.Log
import androidx.lifecycle.*
import com.androiddevs.mvvmnewsapp.Util.Resource
import com.wbconcept.myapplication.Entities.Custom_LiveData_Product
import com.wbconcept.myapplication.Entities.Product_List
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.Repositories.ProductsRepository
import com.wbconcept.myapplication.Util.Rezources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel

class ProductViewModel  @Inject constructor(
    val productRepository: ProductsRepository
) : ViewModel() {

    val productList: MutableLiveData<Resource<Product_List>> = MutableLiveData()
    val categoryList: MutableLiveData<Resource<List<String>>> = MutableLiveData()
    var dbProducts: LiveData<Rezources<List<Product_ListItem>>>
    private val filter = MutableLiveData<String>()
    private val filters = MutableLiveData<Custom_LiveData_Product>()



    init {
        getAllcategories()
        filters.postValue(Custom_LiveData_Product("all",""))
        filter.postValue("all")


        dbProducts = Transformations.switchMap<Custom_LiveData_Product?, Rezources<List<Product_ListItem>>>(
            filters
        ) { id: Custom_LiveData_Product? ->
            productRepository.get_cached_data_custom(filters.value).map {
                Log.d("WiewModel", "data is here")
                when (it.status) {
                    Rezources.Status.LOADING -> {
                        Log.d("WiewModel", "LOADING")

                        Rezources.loading(null)
                    }
                    Rezources.Status.SUCCESS -> {

                        Rezources.success(it.data)
                    }
                    Rezources.Status.ERROR -> {
                        Log.d("WiewModel", "ERROR")
                        Rezources.error(it.message!!, null)
                    }
                }
            }.asLiveData(viewModelScope.coroutineContext)
        }



    }


    fun edit_filter(fil: Custom_LiveData_Product) {
        filters.postValue(fil)
    }


    fun getAllProduct() = viewModelScope.launch {
        productList.postValue(Resource.Loading())
        try {
            val response = productRepository.getAllProduct()
            productList.postValue(hundleProductResponse(response))

        } catch (e: UnknownHostException) {
            productList.postValue(e.message?.let { Resource.Error(it) })
        }
    }

    fun getProductByCategory(category: String) = viewModelScope.launch {
        productList.postValue(Resource.Loading())

        try {
            val response: Response<Product_List>
            if (category == "All") {
                response = productRepository.getAllProduct()
            } else {
                response = productRepository.getProductByCategories(category)
            }
            productList.postValue(hundleProduct_by_categoryResponse(response))

        } catch (e: UnknownHostException) {
            productList.postValue(e.message?.let { Resource.Error(it) })
        }
    }

    fun getAllcategories() = viewModelScope.launch {
        categoryList.postValue(Resource.Loading())

        try {
            val response = productRepository.getCategories()
            categoryList.postValue(hundleCategoryResponse(response))

        } catch (e: UnknownHostException) {
            categoryList.postValue(e.message?.let { Resource.Error(it) })
        }
    }

    private fun hundleProductResponse(response: Response<Product_List>): Resource<Product_List> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hundleProduct_by_categoryResponse(response: Response<Product_List>): Resource<Product_List> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    private fun hundleCategoryResponse(response: Response<List<String>>): Resource<List<String>> {
        if (response.isSuccessful) {
            response.body()?.let { result ->

                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Product_ListItem) = viewModelScope.launch {
        productRepository.insert(article)
    }

    fun saveFavorite(article: Int) = viewModelScope.launch {

        viewModelScope.launch(Dispatchers.IO) {
            productRepository.saveFavorite(article)
        }
    }

    fun deleteFavorite(article: Int) = viewModelScope.launch {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteFavorite(article)
        }
    }

    fun delete(article: Product_ListItem) = viewModelScope.launch {
        productRepository.delete(article)
    }
}