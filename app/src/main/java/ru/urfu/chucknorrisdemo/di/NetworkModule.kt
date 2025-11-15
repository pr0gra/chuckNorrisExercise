package ru.urfu.chucknorrisdemo.di

import android.content.Context
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.data.local.ChuckDataStore
import ru.urfu.chucknorrisdemo.data.repository.ChuckRepository
import ru.urfu.chucknorrisdemo.dataStore
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository

private const val BASE_URL = "https://api.chucknorris.io/"

val networkModule = module {

    single {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChuckApi::class.java)
    }

    single { ChuckDataStore(get<Context>().dataStore) }

    single<IChuckRepository> { ChuckRepository(get(), get()) }
}