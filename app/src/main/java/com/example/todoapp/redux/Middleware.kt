package com.example.todoapp.redux

import android.util.Log
import org.reduxkotlin.createThunkMiddleware
import org.reduxkotlin.middleware

val thunkMiddleware = createThunkMiddleware<AppState>()

val loggerMiddleware = middleware<AppState> { store, next, action ->
    Log.d("Redux", "▶ Dispatching $action")
    val result = next(action)
    Log.d("Redux", "✔ New state: ${store.state}")
    result
}

val crashReporter = middleware<AppState> { _, next, action ->
    try {
        next(action)
    } catch (e: Throwable) {
        // e.g. Crashlytics.logException(e)
        throw e
    }
}
