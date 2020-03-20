package com.uhfsolutions.roomdatabase.Paging.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WebApiClient {

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com/2.2/")
                .addConverterFactory(GsonConverterFactory.create())
    }
    val client: StackExchApi by lazy {
        retrofit
                .build()
                .create(StackExchApi::class.java)
    }


}