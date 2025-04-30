package com.example.todoapp.data.api

import com.example.todoapp.data.model.CreateTodoRequest
import com.example.todoapp.data.model.DeleteResponse
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.UpdateTodoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {
    @GET("api/todos")
    suspend fun getAllTodos(): Response<List<Todo>>

    @GET("api/todos/{id}")
    suspend fun getTodoById(@Path("id") id: String): Response<Todo>

    @POST("api/todos")
    suspend fun createTodo(@Body todo: CreateTodoRequest): Response<Todo>

    @PUT("api/todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: String,
        @Body todo: UpdateTodoRequest
    ): Response<Todo>

    @DELETE("api/todos/{id}")
    suspend fun deleteTodo(@Path("id") id: String): Response<DeleteResponse>
}
