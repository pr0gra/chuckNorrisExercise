package ru.urfu.chucknorrisdemo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.urfu.chucknorrisdemo.presentation.screen.ChuckScreen
import ru.urfu.chucknorrisdemo.ui.theme.ChuckNorrisDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChuckNorrisDemoTheme {
                ChuckScreen()
            }
        }
    }
}