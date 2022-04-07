package ru.profitsw2000.mvpapp.data

import android.os.Handler
import androidx.annotation.MainThread
import ru.profitsw2000.mvpapp.domain.LoginApi
import ru.profitsw2000.mvpapp.domain.LoginUseCase

class LoginUseCaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
    ) : LoginUseCase {

    override fun login(login: String,
                       password: String,
                       @MainThread callback: (Boolean) -> Unit) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun register(login: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun restorePassword(login: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun logout(): Boolean {
        TODO("Not yet implemented")
    }
}