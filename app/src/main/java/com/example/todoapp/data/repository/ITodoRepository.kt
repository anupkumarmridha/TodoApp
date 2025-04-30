package com.example.todoapp.data.repository

import com.example.todoapp.data.model.CreateTodoRequest
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.UpdateTodoRequest


interface ITodoRepository {
    suspend fun getAllTodos(): Result<List<Todo>>
    suspend fun getTodoById(id: String): Result<Todo>
    suspend fun createTodo(todo: CreateTodoRequest): Result<Todo>
    suspend fun updateTodo(id: String, todo: UpdateTodoRequest): Result<Todo>
    suspend fun deleteTodo(id: String): Result<Boolean>
}