package com.example.todoapp.redux

fun appReducer(state: AppState, action: Any): AppState =
    when (action) {
        is Action.LoadTodos ->
            state.copy(isLoading = true, error = null)

        is Action.TodosLoaded ->
            state.copy(todos = action.todos, isLoading = false)

        is Action.LoadError ->
            state.copy(isLoading = false, error = action.message)

        is Action.AddTodoSuccess ->
            state.copy(todos = listOf(action.todo) + state.todos)

        is Action.UpdateTodoSuccess ->
            state.copy(todos = state.todos.map {
                if (it.id == action.todo.id) action.todo else it
            })

        is Action.DeleteTodoSuccess ->
            state.copy(todos = state.todos.filter { it.id != action.response.id })

        else -> state
    }
