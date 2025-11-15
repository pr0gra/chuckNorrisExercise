package ru.urfu.chucknorrisdemo.presentation.state

interface ChuckScreenState {
    var categories: List<String>
    var selectedCategory: String
    var joke: String
    var isLoading: Boolean
    var error: String?
}