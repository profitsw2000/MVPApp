package ru.profitsw2000.mvpapp.ui.login

import androidx.annotation.MainThread
import ru.profitsw2000.mvpapp.utils.Publisher

interface ViewModel {
    val showProgress: Publisher<Boolean>
    val isSignInSuccess: Publisher<Boolean>
    val isRestorePasswordSuccess: Publisher<Boolean>
    val isSignUpSuccess: Publisher<Boolean>
    val errorCode: Publisher<Int?>

    @MainThread
    fun onLogin(login: String, password: String)

    @MainThread
    fun onRestorePassword(email: String)

    @MainThread
    fun onSignUp(email: String, login: String, password: String)
}