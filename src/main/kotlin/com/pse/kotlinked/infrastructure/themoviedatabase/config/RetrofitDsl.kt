package com.pse.kotlinked.infrastructure.themoviedatabase.config

import retrofit2.Retrofit

fun okHttpClient(init: okhttp3.OkHttpClient.Builder.() -> Unit) = okhttp3.OkHttpClient.Builder().apply(init).build()

inline fun <reified T> retrofit(init: Retrofit.Builder.() -> Unit) = Retrofit.Builder().apply(init).build().create(T::class.java)