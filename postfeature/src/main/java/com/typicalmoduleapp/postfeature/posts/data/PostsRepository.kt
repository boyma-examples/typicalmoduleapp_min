package com.typicalmoduleapp.postfeature.posts.data

import com.typicalmoduleapp.postfeature.posts.data.dtos.PostDto
import io.reactivex.Single
import javax.inject.Inject

interface PostsRepository {
    fun getContacts(): Single<List<PostDto>>
}

class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi
) : PostsRepository {
    override fun getContacts(): Single<List<PostDto>> {
        /**
        //demonstrates how to throw and show custom error
        return try {
        throw IllegalArgumentException()
        }catch (e: Exception){
        Single.error(CustomMessageException("asd", ErrorType.FIELD))
        }
        //
         */
        /*return Single.error(HttpException(Response.error<String>(
            400,
            ResponseBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                Gson().toJson(ApiErrorDto("asd", "asdd")).toString()
            ))))*/
        return api.getContacts()
    }

}
