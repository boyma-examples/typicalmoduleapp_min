package com.typicalmoduleapp.postfeature.posts.data.dtos

import com.google.gson.annotations.SerializedName

class PostDto(
    @SerializedName("userId") val userId: String,
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)
