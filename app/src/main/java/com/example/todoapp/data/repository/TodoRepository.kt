package com.example.todoapp.data.repository

import android.util.Log
import com.example.todoapp.data.api.TodoApi
import com.example.todoapp.data.model.CreateTodoRequest
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.UpdateTodoRequest

import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoApi
) : ITodoRepository {
    override suspend fun getAllTodos(): Result<List<Todo>> {
        return try {
            val response = api.getAllTodos()
            Log.d("TodoRepository", "Response: ${response.body()}")
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch todos: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTodoById(id: String): Result<Todo> {
        return try {
            val response = api.getTodoById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Todo not found"))
            } else {
                Result.failure(Exception("Failed to fetch todo: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTodo(todo: CreateTodoRequest): Result<Todo> {
        return try {
            val response = api.createTodo(todo)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to create todo"))
            } else {
                Result.failure(Exception("Failed to create todo: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTodo(
        id: String,
        todo: UpdateTodoRequest
    ): Result<Todo> {
        return try {
            val response = api.updateTodo(id, todo)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to update todo"))
            } else {
                Result.failure(Exception("Failed to update todo: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTodo(id: String): Result<Boolean> {
        return try {
            val response = api.deleteTodo(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to delete todo: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}