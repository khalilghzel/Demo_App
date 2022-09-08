package com.wbconcept.myapplication.di

import android.content.Context
import com.wbconcept.myapplication.DB.CartDao
import com.wbconcept.myapplication.DB.ProductDao
import com.wbconcept.myapplication.DB.ProductsDataBase
import com.wbconcept.myapplication.Repositories.CartRepository
import com.wbconcept.myapplication.Repositories.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object  DBModule {

    @Provides
    fun provideProductDao(@ApplicationContext appContext: Context) : ProductDao {
        return ProductsDataBase(appContext).getProductDao()
    }



    @Provides
    fun provideProductRepository(@ApplicationContext appContext: Context) = ProductsRepository(ProductsDataBase(appContext))




    @Provides
    fun provideCartDao(@ApplicationContext appContext: Context) : CartDao {
        return ProductsDataBase(appContext).getCartDao()
    }

    @Provides
    fun provideCartRepository(cartDao: CartDao) = CartRepository(cartDao)


}