package com.example.todoapp.ui.feature.Todo

import androidx.lifecycle.ViewModel
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
    fun loadTodos(limit: Int? = null, skip: Int? = null) {
        store.dispatch(thunks.fetchTodos(limit, skip))
    }

    fun addTodo(text: String, userId: Int) {
        store.dispatch(thunks.addTodo(text, userId))
    }

    fun updateTodo(id: Int, completed: Boolean) {
        store.dispatch(thunks.updateTodo(id, completed))
    }

    fun deleteTodo(id: Int) {
        store.dispatch(thunks.deleteTodo(id))
    }
}