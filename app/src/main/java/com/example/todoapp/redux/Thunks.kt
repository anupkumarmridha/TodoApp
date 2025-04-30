package com.example.todoapp.redux

import android.util.Log
import com.example.todoapp.data.model.CreateTodoRequest
import com.example.todoapp.data.model.DeleteResponse
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.UpdateTodoRequest
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

    fun fetchTodos(): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            repository.getAllTodos()
                .onSuccess { todos ->
                    Log.d("TodoThunks", "Todos fetched: $todos")
                    dispatch(Action.TodosLoaded(todos))
                }
                .onFailure { e ->
                    dispatch(Action.LoadError(e.message ?: "Fetch failed"))
                }
        }
    }

    fun addTodo(title: String, description: String? = null, priority: Priority = Priority.MEDIUM): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            val request = CreateTodoRequest(title, description, priority)
            repository.createTodo(request)
                .onSuccess { todo ->
                    dispatch(Action.AddTodoSuccess(todo))
                }
                .onFailure { e ->
                    dispatch(Action.LoadError(e.localizedMessage ?: "Add failed"))
                }
        }
    }

    fun updateTodo(id: String, title: String? = null, description: String? = null,
                   completed: Boolean? = null, priority: Priority? = null): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            val request = UpdateTodoRequest(title, description, completed, priority)
            repository.updateTodo(id, request)
                .onSuccess { todo ->
                    dispatch(Action.UpdateTodoSuccess(todo))
                }
                .onFailure { e ->
                    dispatch(Action.LoadError(e.localizedMessage ?: "Update failed"))
                }
        }
    }

    fun deleteTodo(id: String): Thunk<AppState> = { dispatch, _, _ ->
        dispatch(Action.LoadTodos)
        scope.launch {
            repository.deleteTodo(id)
                .onSuccess { success ->
                    val response = DeleteResponse(id, "Todo removed")
                    dispatch(Action.DeleteTodoSuccess(response))
                }
                .onFailure { e ->
                    dispatch(Action.LoadError(e.localizedMessage ?: "Delete failed"))
                }
        }
    }
}
