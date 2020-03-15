package com.uhfsolutions.roomdatabase.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private var retrofit:Retrofit
    init {
         retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val jsonApi = retrofit.create(RetrofitApi::class.java)

}