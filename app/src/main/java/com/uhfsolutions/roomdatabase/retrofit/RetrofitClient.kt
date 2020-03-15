package com.uhfsolutions.roomdatabase.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())

    }
    val client: RetrofitApi by lazy {
        retrofit
            .build()
            .create(RetrofitApi::class.java)
    }

}