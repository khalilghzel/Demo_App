package com.wbconcept.myapplication.Repositories

import com.wbconcept.myapplication.DB.ProductsDataBase


class FavoriteRepository(
    val db: ProductsDataBase
) {




    fun get_all_favoritesDB() =
        db.getFavoriteDao().get_all_products()

    suspend fun saveFavorite(article: Int) =
        db.getFavoriteDao().set_fav(article)

    suspend fun deleteFavorite(article: Int) =
        db.getFavoriteDao().remove_fav(article)

}