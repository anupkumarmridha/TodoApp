package com.example.todoapp.redux

import com.example.todoapp.data.model.Todo

data class AppState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)