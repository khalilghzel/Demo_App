package com.wbconcept.myapplication.Api

import com.wbconcept.myapplication.Entities.LoginRequest
import com.wbconcept.myapplication.Entities.Login_response
import com.wbconcept.myapplication.Entities.Product_List
import com.wbconcept.myapplication.Entities.DB.Product_ListItem
import com.wbconcept.myapplication.Util.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

//    @POST("/api/Authentification/Login")
//    fun login(@Body request: LoginRequest): Call<Login_response>


    @POST("/api/Authentification/Login")
    suspend fun login(@Body request: LoginRequest): Response<Login_response>


    @GET("/products")
    suspend fun getProductList(): Response<Product_List>


    @GET("/products")
      fun getProductListtest(): Flow<ApiResponse<List<Product_ListItem>>>

    @GET("/products/categories")
    suspend fun getCategories(): Response<List<String>>


    @GET("/products/category/{category}")
    suspend fun getProductByCategories(@Path("category") category : String ): Response<Product_List>



}