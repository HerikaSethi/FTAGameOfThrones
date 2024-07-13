package com.herika.flipkartassignment.domainlayer

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://jsonkeeper.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .hostnameVerifier { hostname, session -> hostname == "jsonkeeper.com" }
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
