package com.example.todoapp.redux

import com.example.todoapp.data.model.DeleteResponse
import com.example.todoapp.data.model.Todo

sealed class Action {
    object LoadTodos : Action()
    data class TodosLoaded(val todos: List<Todo>) : Action()
    data class LoadError(val message: String) : Action()
    data class AddTodoSuccess(val todo: Todo) : Action()
    data class UpdateTodoSuccess(val todo: Todo) : Action()
    data class DeleteTodoSuccess(val response: DeleteResponse) : Action()
}
