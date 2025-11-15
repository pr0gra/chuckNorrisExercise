package ru.urfu.chucknorrisdemo.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import ru.urfu.chucknorrisdemo.presentation.viewModel.ChuckViewModel

@Composable
fun ChuckScreen() {
    val viewModel = koinViewModel<ChuckViewModel>()
    val viewState = viewModel.viewState

    var expanded by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { expanded = true }) {
            Text(viewState.selectedCategory.ifEmpty { "Выбрать категорию" })
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            viewState.categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category) },
                    onClick = {
                        expanded = false
                        viewModel.onCategoryClicked(category)
                    }
                )
            }
        }

        Spacer(Modifier.height(16.dp))
        when {
            viewState.isLoading -> {
                CircularProgressIndicator()
            }

            viewState.error != null -> {
                Text(
                    text = viewState.error ?: "",
                    color = Color.Red,
                )
            }

            viewState.joke.isNotEmpty() -> {
                Text(
                    text = viewState.joke,
                    fontSize = 18.sp,
                )
            }

            else -> {
                Text(
                    text = "Выберите категорию, чтобы загрузить шутку.",
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview
@Composable
fun ChuckScreenPreview() {
    ChuckScreen()
}
