package com.typicalmoduleapp.postfeature.posts.di

import com.typicalmoduleapp.postfeature.posts.data.PostsApi
import com.typicalmoduleapp.postfeature.posts.data.PostsRepository
import com.typicalmoduleapp.postfeature.posts.data.PostsRepositoryImpl
import com.typicalmoduleapp.postfeature.posts.domain.PostsInteractor
import com.typicalmoduleapp.postfeature.posts.domain.PostsInteractorImpl
import dagger.Module
import dagger.Provides
import ru.typicalmoduleapp.appcore.common.config.ApiBaseUrl
import ru.typicalmoduleapp.appcore.common.net.ApiFactory
import ru.typicalmoduleapp.appcore.common.net.ApiFactoryImpl
import ru.typicalmoduleapp.appcore.common.net.OkHttpClientFactorySimpleImpl
import ru.typicalmoduleapp.appcore.common.net.createApi
import ru.typicalmoduleapp.appcore.common.net.errorhandle.CallAdapterFactory
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PostsApiFactory

@Module
class PostsModule {

    //demonstrates call to other api, delete with annotation if want to call base
    //or add annotation to provideApi - apiFactory
    @Provides
    @PostsApiFactory
    fun provideApiFactory(
        callAdapterFactory: CallAdapterFactory,
        okHttpClientFactorySimpleImpl: OkHttpClientFactorySimpleImpl
    ): ApiFactory = ApiFactoryImpl(
        ApiBaseUrl("https://jsonplaceholder.typicode.com"),
        okHttpClientFactorySimpleImpl.create(),
        callAdapterFactory
    )

    @Provides
    fun providePostsApi(@PostsApiFactory apiFactory: ApiFactory): PostsApi = apiFactory.createApi()

    @Provides
    fun providePostsInteractor(impl: PostsInteractorImpl) =
        impl as PostsInteractor

    @Provides
    fun providePostsRepository(impl: PostsRepositoryImpl) =
        impl as PostsRepository
}