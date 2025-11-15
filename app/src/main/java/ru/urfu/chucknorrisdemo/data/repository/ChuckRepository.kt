package ru.urfu.chucknorrisdemo.data.repository

import ru.urfu.chucknorrisdemo.data.api.ChuckApi
import ru.urfu.chucknorrisdemo.data.local.ChuckDataStore
import ru.urfu.chucknorrisdemo.domain.model.Joke
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository

class ChuckRepository(
    private val api: ChuckApi,
    private val store: ChuckDataStore,
) : IChuckRepository {

    override suspend fun getCategories(): List<String> {
        return try {
            api.getCategories()
        } catch (e: Exception) {
            val cachedCategory = store.getLastCategory()
            if (cachedCategory != null && cachedCategory.isNotEmpty()) {
                listOf(cachedCategory)
            } else {
                emptyList()
            }
        }
    }

    override suspend fun getJoke(category: String): Joke {
        return try {
            val dto = api.getRandomJoke(category)
            val joke = Joke(dto.id, dto.value, dto.categories?.firstOrNull() ?: category)
            store.saveJoke(joke)
            joke
        } catch (_: Exception) {
            store.getLastJoke()
                ?: Joke("no-net", "Нет сети и нет сохранённых шуток.", category)
        }
    }
}