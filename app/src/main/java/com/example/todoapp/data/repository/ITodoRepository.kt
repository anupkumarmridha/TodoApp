package com.example.todoapp.data.repository

import com.example.todoapp.data.model.DeletedTodoResponse
import com.example.todoapp.data.model.Todo

interface ITodoRepository {
    suspend fun fetchTodos(limit: Int? = null, skip: Int? = null): List<Todo>
    suspend fun addTodo(text: String, userId: Int): Todo
    suspend fun updateTodo(id: Int, completed: Boolean): Todo
    suspend fun deleteTodo(id: Int): DeletedTodoResponse
}