package com.typicalmoduleapp.postfeature.posts.di

import com.typicalmoduleapp.postfeature.posts.view.PostsFragment
import dagger.Component
import ru.typicalmoduleapp.appcore.commondi.AppRootComponent
import ru.typicalmoduleapp.appcore.commondi.scopes.PerActivityScope
import ru.typicalmoduleapp.appcore.rootComponent

@PerActivityScope
@Component(
    dependencies = [AppRootComponent::class],
    modules = [PostsModule::class]
)
interface PostsComponent {
    fun inject(fragment: PostsFragment)
}

fun createPostsComponent(fragment: PostsFragment): PostsComponent =
    DaggerPostsComponent.builder()
        .appRootComponent(fragment.rootComponent)
        .build()
