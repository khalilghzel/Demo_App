package com.wbconcept.myapplication.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wbconcept.myapplication.Entities.DB.Cart_Item
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
@TypeConverters(value = [MyTypeConverters::class])
@Database(
    entities = [Product_ListItem::class, Cart_Item::class],
    version = 14

)
abstract class ProductsDataBase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao
    abstract fun getCartDao(): CartDao
    abstract fun getFavoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: ProductsDataBase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDataBase(context).also { instance = it }

        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ProductsDataBase::class.java,
                "product_db.db"
            ).fallbackToDestructiveMigration().build()
    }
}