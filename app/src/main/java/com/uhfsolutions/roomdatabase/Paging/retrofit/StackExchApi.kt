package com.uhfsolutions.roomdatabase.Paging.retrofit

import com.uhfsolutions.roomdatabase.Paging.model.StackExchResp
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StackExchApi {

    @GET("answers")
    fun getData(@Query("page")page:Int,
                @Query("pagesize")size:Int,
                @Query("site")site:String): Call<StackExchResp>
}