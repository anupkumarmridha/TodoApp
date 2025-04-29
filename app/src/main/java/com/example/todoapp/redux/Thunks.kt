package com.example.todoapp.redux

import com.example.todoapp.data.repository.ITodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk

/**
 * Defines all async action creators (thunks) for TODO CRUD operations.
 */
class TodoThunks(
    private val repository: ITodoRepository
) {
    // A dedicated scope for I/O–bound work
    private val scope = CoroutineScope(Dispatchers.IO)

    /** Fetch all todos, dispatching LoadTodos → TodosLoaded / LoadError */
    fun fetchTodos(limit: Int? = null, skip: Int? = null): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            try {
                val todos = repository.fetchTodos(limit, skip)
                dispatch(Action.TodosLoaded(todos))
            } catch (e: Exception) {
                dispatch(Action.LoadError(e.localizedMessage ?: "Fetch failed"))
            }
        }
    }

    /** Create a new todo, dispatching LoadTodos → AddTodoSuccess / LoadError */
    fun addTodo(text: String, userId: Int): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            try {
                val todo = repository.addTodo(text, userId)
                dispatch(Action.AddTodoSuccess(todo))
            } catch (e: Exception) {
                dispatch(Action.LoadError(e.localizedMessage ?: "Add failed"))
            }
        }
    }

    /** Update an existing todo, dispatching LoadTodos → UpdateTodoSuccess / LoadError */
    fun updateTodo(id: Int, completed: Boolean): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            try {
                val todo = repository.updateTodo(id, completed)
                dispatch(Action.UpdateTodoSuccess(todo))
            } catch (e: Exception) {
                dispatch(Action.LoadError(e.localizedMessage ?: "Update failed"))
            }
        }
    }

    /** Delete a todo, dispatching LoadTodos → DeleteTodoSuccess / LoadError */
    fun deleteTodo(id: Int): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            try {
                val response = repository.deleteTodo(id)
                dispatch(Action.DeleteTodoSuccess(response))
            } catch (e: Exception) {
                dispatch(Action.LoadError(e.localizedMessage ?: "Delete failed"))
            }
        }
    }
}
