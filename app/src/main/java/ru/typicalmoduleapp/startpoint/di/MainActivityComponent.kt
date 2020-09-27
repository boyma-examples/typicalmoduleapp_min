package ru.typicalmoduleapp.startpoint.di

import dagger.Component
import ru.typicalmoduleapp.appcore.commondi.AppRootComponent
import ru.typicalmoduleapp.appcore.commondi.scopes.PerActivityScope
import ru.typicalmoduleapp.appcore.rootComponent
import ru.typicalmoduleapp.startpoint.view.MainActivity

@PerActivityScope
@Component(dependencies = [AppRootComponent::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
}

fun createMainActivityComponent(activity: MainActivity): MainActivityComponent =
    DaggerMainActivityComponent.builder()
        .appRootComponent(activity.rootComponent)
        .build()
