package com.typicalmoduleapp.postfeature.posts.domain

import com.typicalmoduleapp.postfeature.posts.data.PostsRepository
import com.typicalmoduleapp.postfeature.posts.data.dtos.PostDto
import io.reactivex.Single
import ru.typicalmoduleapp.utils.rx.schedule
import javax.inject.Inject

interface PostsInteractor {
    fun getContacts(): Single<List<PostDto>>
}

class PostsInteractorImpl @Inject constructor(
    private val repository: PostsRepository
) : PostsInteractor {

    override fun getContacts(): Single<List<PostDto>> {
        return repository.getContacts().schedule()
    }
}