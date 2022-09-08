package com.wbconcept.myapplication.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wbconcept.myapplication.Entities.DB.Product_ListItem

@Dao
interface FavoriteDao {





    @Query("select products.* from products   where   products.favorite = 1")
     fun get_all_products(): LiveData<List<Product_ListItem>>


    @Query("update products  set favorite = 1 where products.id = :id ")
    fun set_fav(id:Int)

    @Query("update products  set favorite = 0 where products.id = :id ")
    fun remove_fav(id:Int)


}