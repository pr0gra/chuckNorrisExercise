package ru.urfu.chucknorrisdemo.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import ru.urfu.chucknorrisdemo.domain.model.Joke

class ChuckDataStore(private val dataStore: DataStore<Preferences>) {


    companion object {
        private val LAST_JOKE_TEXT = stringPreferencesKey("last_joke_text")
        private val LAST_JOKE_CATEGORY = stringPreferencesKey("last_joke_category")
    }

    suspend fun saveJoke(joke: Joke) {
        dataStore.edit {
            it[LAST_JOKE_TEXT] = joke.text
            it[LAST_JOKE_CATEGORY] = joke.category ?: ""
        }
    }

    suspend fun getLastJoke(): Joke? {
        val prefs = dataStore.data.firstOrNull() ?: return null
        val text = prefs[LAST_JOKE_TEXT] ?: return null
        val category = prefs[LAST_JOKE_CATEGORY]
        return Joke(id = "local", text = text, category = category)
    }

    suspend fun getLastCategory(): String? {
        val prefs = dataStore.data.firstOrNull() ?: return null
        return prefs[LAST_JOKE_CATEGORY]
    }
}