package com.wbconcept.myapplication.Repositories

import com.wbconcept.myapplication.DB.CartDao
import com.wbconcept.myapplication.DB.ProductsDataBase
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import javax.inject.Inject


class CartRepository@Inject constructor(private val cartDao: CartDao) {
    suspend fun insert(article: Cart_Item) =
        cartDao.upsert(article)
    suspend fun update(article: Cart_Item) =
        cartDao.update(article)

    suspend fun delete(article: Cart_Item) =
        cartDao.delete(article)

    fun get_all_productsDB() =
        cartDao.get_all_products()

}