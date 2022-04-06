package ru.profitsw2000.mvpapp

import android.app.Application
import android.content.Context
import ru.profitsw2000.mvpapp.data.TestLoginApiImpl
import ru.profitsw2000.mvpapp.domain.LoginApi

class App : Application() {
    private val loginApi: LoginApi by lazy { TestLoginApiImpl() }
}

val Context.app: App
    get() {
        return applicationContext as App
    }