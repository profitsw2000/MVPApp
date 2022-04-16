package ru.profitsw2000.mvpapp

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import ru.profitsw2000.mvpapp.data.LoginUseCaseImpl
import ru.profitsw2000.mvpapp.data.TestLoginApiImpl
import ru.profitsw2000.mvpapp.domain.LoginApi
import ru.profitsw2000.mvpapp.domain.LoginUseCase

class App : Application() {
    private val loginApi: LoginApi by lazy { TestLoginApiImpl() }
    val loginUseCase: LoginUseCase by lazy {
        LoginUseCaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }