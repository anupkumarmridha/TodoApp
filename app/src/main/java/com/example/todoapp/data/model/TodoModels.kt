package com.example.todoapp.data.model

import kotlinx.serialization.Serializable

// Matches DummyJSON /todos response
@Serializable
data class Todo(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)

@Serializable
data class TodosResponse(
    val todos: List<Todo>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

@Serializable
data class TodoRequest(
    val todo: String,
    val completed: Boolean,
    val userId: Int
)

@Serializable
data class TodoUpdate(
    val completed: Boolean
)

@Serializable
data class DeletedTodoResponse(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int,
    val isDeleted: Boolean,
    val deletedOn: String
)
