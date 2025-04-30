package com.example.todoapp.ui.feature.Todo

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.model.CreateTodoRequest
import com.example.todoapp.data.model.Priority
import com.example.todoapp.data.model.UpdateTodoRequest
import com.example.todoapp.data.repository.ITodoRepository
import com.example.todoapp.redux.AppState
import com.example.todoapp.redux.appReducer
import com.example.todoapp.redux.crashReporter
import com.example.todoapp.redux.loggerMiddleware
import com.example.todoapp.redux.TodoThunks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: ITodoRepository
) : ViewModel() {

    private val thunkMiddleware = createThunkMiddleware<AppState>()

    // Create the TodoThunks instance
    private val thunks = TodoThunks(repository)

    private val store = createThreadSafeStore(
        ::appReducer,
        AppState(),
        applyMiddleware(
            thunkMiddleware,
            loggerMiddleware,
            crashReporter
        )
    )

    private val _uiState = MutableStateFlow(store.state)
    val uiState: StateFlow<AppState> = _uiState.asStateFlow()

    init {
        // Subscribe store updates to StateFlow
        store.subscribe {
            _uiState.value = store.state
        }
        // Kick off initial load
        loadTodos()
    }

    // Expose simple methods that dispatch thunks
    fun loadTodos() {
        store.dispatch(thunks.fetchTodos())
    }

    fun addTodo(title: String, description: String? = null, priority: Priority = Priority.MEDIUM) {
        store.dispatch(thunks.addTodo(title, description, priority))
    }

    fun updateTodo(
        id: String,
        title: String? = null,
        description: String? = null,
        completed: Boolean? = null,
        priority: Priority? = null
    ) {
        store.dispatch(thunks.updateTodo(id, title, description, completed, priority))
    }

    fun deleteTodo(id: String) {
        store.dispatch(thunks.deleteTodo(id))
    }
}