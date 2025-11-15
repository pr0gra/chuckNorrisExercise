package ru.urfu.chucknorrisdemo.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.urfu.chucknorrisdemo.domain.repository.IChuckRepository
import ru.urfu.chucknorrisdemo.presentation.state.ChuckScreenState

class ChuckViewModel(private val repo: IChuckRepository): ViewModel() {
    private val mutableState = MutableChuckState()
    val viewState = mutableState as ChuckScreenState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            mutableState.isLoading = true
            try {
                val categories = repo.getCategories()
                if (categories.isEmpty()) {
                    mutableState.error = "Нет сети и нет сохранённых данных"
                } else {
                    mutableState.categories = categories
                    mutableState.error = null
                }
            } catch (e: Exception) {
                mutableState.error = "Ошибка загрузки категорий"
            } finally {
                mutableState.isLoading = false
            }
        }
    }

    fun onCategoryClicked(category: String) {
        mutableState.selectedCategory = category
        viewModelScope.launch {
            mutableState.isLoading = true
            try {
                val joke = repo.getJoke(category)
                mutableState.joke = joke.text
                mutableState.error = null
            } catch (_: Exception) {
                mutableState.error = "Нет сети, показана сохранённая шутка"
                mutableState.joke = repo.getJoke(category).text
            } finally {
                mutableState.isLoading = false
            }
        }
    }

    private class MutableChuckState : ChuckScreenState {
        override var categories by mutableStateOf(emptyList<String>())
        override var selectedCategory by mutableStateOf("")
        override var joke by mutableStateOf("")
        override var isLoading by mutableStateOf(false)
        override var error by mutableStateOf<String?>(null)
    }
}