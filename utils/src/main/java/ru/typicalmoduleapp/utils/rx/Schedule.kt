package ru.typicalmoduleapp.utils.rx

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Suppress("unused", "nothing_to_inline", "unchecked_cast")
inline fun <T> Single<T>.schedule(): Single<T> =
    observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

@Suppress("unused", "nothing_to_inline", "unchecked_cast")
inline fun <T> Observable<T>.schedule(): Observable<T> =
    observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())

@Suppress("unused", "nothing_to_inline")
inline fun Completable.schedule(): Completable =
    observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
