package com.example.todoapp.data.api

import com.example.todoapp.data.model.DeletedTodoResponse
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.TodoRequest
import com.example.todoapp.data.model.TodoUpdate
import com.example.todoapp.data.model.TodosResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null
    ): TodosResponse

    @POST("todos/add")
    suspend fun addTodo(@Body req: TodoRequest): Todo

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body update: TodoUpdate
    ): Todo

    @DELETE("todos/{id}")
    suspend fun deleteTodo(@Path("id") id: Int): DeletedTodoResponse
}
