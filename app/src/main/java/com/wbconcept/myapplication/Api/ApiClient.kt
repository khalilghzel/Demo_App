package com.wbconcept.myapplication.Api

import com.wbconcept.myapplication.Api.FlowCall.FlowCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class ApiClient {


    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)

                .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request =
                        chain.request().newBuilder().addHeader("Accept", "application/json")
                            .addHeader("content-type", "application/json").build()
                    return chain.proceed(request)
                }
            })


                .addInterceptor(logging)
                .build()


             Retrofit.Builder().baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())    .addCallAdapterFactory(
                    FlowCallAdapterFactory.create())
                .client(client).build()
        }
        val api by lazy {
            retrofit.create(ApiService::class.java)
        }

    }



}
