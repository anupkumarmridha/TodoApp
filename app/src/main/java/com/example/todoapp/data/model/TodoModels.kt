package com.example.todoapp.data.model

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("_id")
    val id: String = "",
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

enum class Priority {
    @SerializedName("low") LOW,
    @SerializedName("medium") MEDIUM,
    @SerializedName("high") HIGH
}

data class CreateTodoRequest(
    val title: String,
    val description: String? = null,
    val priority: Priority = Priority.MEDIUM
)

data class UpdateTodoRequest(
    val title: String? = null,
    val description: String? = null,
    val completed: Boolean? = null,
    val priority: Priority? = null
)

data class ErrorResponse(
    val message: String
)

data class DeleteResponse(
    @SerializedName("_id")
    val id: String,
    val message: String
)
