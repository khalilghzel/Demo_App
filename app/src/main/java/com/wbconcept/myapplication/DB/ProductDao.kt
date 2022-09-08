package com.wbconcept.myapplication.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(article : Product_ListItem):Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertlist(article : List<Product_ListItem> )


    @Query("select * from products")
    fun get_all_products(): LiveData<List<Product_ListItem>>
    @Query("select *  from products")
    fun get_all_productstest(): Flow<List<Product_ListItem>>


    @Query("select *  from products where title like  :name ")
    fun filter_product_query(name : String): Flow<List<Product_ListItem>>



    @Query("select *  from products where category = :categ")
    fun get_all_productstestby_categ(categ:String?): Flow<List<Product_ListItem>>


    @Query("select * from products where category =:category ")
     fun get_all_products_by_category(category:String): LiveData<List<Product_ListItem>>

    @Delete()
    fun delete(article: Product_ListItem)


}