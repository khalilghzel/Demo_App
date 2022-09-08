package com.wbconcept.myapplication.Repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.wbconcept.myapplication.Api.ApiClient
import com.wbconcept.myapplication.DB.ProductsDataBase
import com.wbconcept.myapplication.Entities.Custom_LiveData_Product
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.Util.Rezources
import com.wbconcept.myapplication.Util.networkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject


class ProductsRepository @Inject constructor(
    val db: ProductsDataBase


) {
    suspend fun getAllProduct() =
        ApiClient.api.getProductList()

    suspend fun syncProducts() {
        ApiClient.api.getProductList().body()?.let { db.getProductDao().upsertlist(it.getList()) }

    }
    // suspend fun EventStore.awaitAll(): List<MyEvent> = async { all() }.await()

    suspend fun getCategories() =
        ApiClient.api.getCategories()


    fun get_cached_data(category: String?): Flow<Rezources<List<Product_ListItem>>> {
        return networkBoundResource(
            fetchFromLocal = {
                when (category?.lowercase()) {
                    "all" -> db.getProductDao().get_all_productstest()
                    else -> db.getProductDao().get_all_productstestby_categ(category)
                }
            },
            shouldFetchFromRemote = { it.isNullOrEmpty() },
            // shouldFetchFromRemote = {   it == null || it.isEmpty() },
            fetchFromRemote = { ApiClient.api.getProductListtest() },
            processRemoteResponse = { },
            saveRemoteData = { db.getProductDao().upsertlist(it) },
            onFetchFailed = { _, _ -> Log.d("error", "ERRROOOOOR") }
        ).flowOn(Dispatchers.IO)
    }


    fun get_cached_data_custom(params: Custom_LiveData_Product?): Flow<Rezources<List<Product_ListItem>>> {
        params?.let { Log.d("product repository", it.query) }

        return networkBoundResource(
            fetchFromLocal = {
                when (params?.category?.lowercase()) {
                    "all" -> db.getProductDao().get_all_productstest()
                    ""->     db.getProductDao().filter_product_query(params.query)
                    else -> db.getProductDao().get_all_productstestby_categ(params?.category)
                }
            },
            shouldFetchFromRemote = { it.isNullOrEmpty() },
            // shouldFetchFromRemote = {   it == null || it.isEmpty() },
            fetchFromRemote = { ApiClient.api.getProductListtest() },
            processRemoteResponse = { },
            saveRemoteData = { db.getProductDao().upsertlist(it) },
            onFetchFailed = { _, _ -> Log.d("error", "ERRROOOOOR") }
        ).flowOn(Dispatchers.IO)
    }



    suspend fun getProductByCategories(category: String) =
        ApiClient.api.getProductByCategories(category)

    suspend fun insert(article: Product_ListItem) =
        db.getProductDao().upsert(article)

    suspend fun saveFavorite(article: Int) =
        db.getFavoriteDao().set_fav(article)

    suspend fun deleteFavorite(article: Int) =
        db.getFavoriteDao().remove_fav(article)

    suspend fun insertList(article: List<Product_ListItem>) =
        db.getProductDao().upsertlist(article)

    suspend fun delete(article: Product_ListItem) =
        db.getProductDao().delete(article)


    fun get_all_productsDB() =
        db.getProductDao().get_all_products()


    fun get_all_productsDB_by_category(category: String):LiveData<List<Product_ListItem>> {
        if (category.lowercase(Locale.getDefault()).equals("all"))
          return  db.getProductDao().get_all_products()
        else
            return  db.getProductDao().get_all_products_by_category(category)

    }


}