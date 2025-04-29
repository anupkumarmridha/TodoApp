package com.example.todoapp.di

import com.example.todoapp.data.api.TodoApi
import com.example.todoapp.data.repository.ITodoRepository
import com.example.todoapp.data.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides @Singleton
    fun provideTodoApi(retrofit: Retrofit): TodoApi =
        retrofit.create(TodoApi::class.java)

    @Provides @Singleton
    fun provideTodoRepository(api: TodoApi): ITodoRepository =
        TodoRepository(api)
}
