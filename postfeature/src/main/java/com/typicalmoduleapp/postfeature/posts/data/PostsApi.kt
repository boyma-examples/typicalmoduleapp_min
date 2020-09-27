package com.typicalmoduleapp.postfeature.posts.data

import com.typicalmoduleapp.postfeature.posts.data.dtos.PostDto
import io.reactivex.Single
import retrofit2.http.GET

interface PostsApi {
    @GET("/posts")
    fun getContacts(): Single<List<PostDto>>

}