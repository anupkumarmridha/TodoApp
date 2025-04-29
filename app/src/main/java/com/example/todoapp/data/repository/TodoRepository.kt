package com.example.todoapp.data.repository

import com.example.todoapp.data.api.TodoApi
import com.example.todoapp.data.model.TodoRequest
import com.example.todoapp.data.model.TodoUpdate
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoApi
) : ITodoRepository {
    override suspend fun fetchTodos(limit: Int?, skip: Int?) =
        api.getTodos(limit, skip).todos

    override suspend fun addTodo(text: String, userId: Int) =
        api.addTodo(TodoRequest(text, false, userId))

    override suspend fun updateTodo(id: Int, completed: Boolean) =
        api.updateTodo(id, TodoUpdate(completed))

    override suspend fun deleteTodo(id: Int) =
        api.deleteTodo(id)
}