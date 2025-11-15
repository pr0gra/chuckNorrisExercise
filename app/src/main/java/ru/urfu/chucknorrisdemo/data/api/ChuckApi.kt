package ru.urfu.chucknorrisdemo.data.api

import retrofit2.http.GET
import retrofit2.http.Query

data class JokeDto(
    val id: String,
    val value: String,
    val categories: List<String>?
)

interface ChuckApi {
    @GET("jokes/categories")
    suspend fun getCategories(): List<String>

    @GET("jokes/random")
    suspend fun getRandomJoke(@Query("category") category: String): JokeDto
}