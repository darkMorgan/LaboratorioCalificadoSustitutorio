package com.valdez.henry.laboratoriocalificadosustitutorio.network

import com.valdez.henry.laboratoriocalificadosustitutorio.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}