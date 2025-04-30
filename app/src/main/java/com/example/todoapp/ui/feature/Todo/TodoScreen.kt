package com.example.todoapp.ui.feature.Todo

import AddTodoDialog
import EditTodoDialog
import ErrorView
import LoadingView
import TodoList
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.data.model.Todo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = hiltViewModel()) {
    val state = viewModel.uiState.collectAsState().value
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var todoToEdit by remember { mutableStateOf<Todo?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Todo List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            when {
                state.isLoading -> LoadingView()
                state.error != null -> ErrorView(state.error)
                else -> TodoList(
                    todos = state.todos,
                    onToggle = { todo ->
                        viewModel.updateTodo(
                            id = todo.id,
                            completed = !todo.completed
                        )
                    },
                    onDelete = { todo ->
                        viewModel.deleteTodo(todo.id)
                    },
                    onEdit = { todo ->
                        todoToEdit = todo
                        showEditDialog = true
                    }
                )
            }

            if (showAddDialog) {
                AddTodoDialog(
                    onAdd = { title, description, priority ->
                        viewModel.addTodo(title, description, priority)
                        showAddDialog = false
                    },
                    onDismiss = { showAddDialog = false }
                )
            }

            todoToEdit?.let { todo ->
                if (showEditDialog) {
                    EditTodoDialog(
                        todo = todo,
                        onEdit = { title, description, priority ->
                            viewModel.updateTodo(
                                id = todo.id,
                                title = title,
                                description = description,
                                priority = priority
                            )
                            showEditDialog = false
                            todoToEdit = null
                        },
                        onDismiss = {
                            showEditDialog = false
                            todoToEdit = null
                        }
                    )
                }
            }
        }
    }
}
