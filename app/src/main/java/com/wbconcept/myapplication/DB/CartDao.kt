package com.wbconcept.myapplication.DB

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Cart_Object

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(cart : Cart_Item):Long

    @Update
    fun update(cart: Cart_Item)

    @Query("select products.*, 0 as selected,  carts.product_id, carts.color, carts.id as cart_id  ,carts.size,carts.quantity  from carts , products where products.id = carts.product_id")
    fun get_all_products(): LiveData<List<Cart_Object>>


    @Delete()
    fun delete(article: Cart_Item)


}