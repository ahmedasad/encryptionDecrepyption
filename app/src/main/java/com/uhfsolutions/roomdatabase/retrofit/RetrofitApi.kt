package com.uhfsolutions.roomdatabase.retrofit

import com.uhfsolutions.roomdatabase.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApi {
    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}