package ru.profitsw2000.mvpapp

import android.app.Application
import android.content.Context

class App : Application() {
    private val loginApi: LoginApi by lazy { TestLoginApiImpl() }
}

val Context.app: App
    get() {
        return applicationContext as App
    }