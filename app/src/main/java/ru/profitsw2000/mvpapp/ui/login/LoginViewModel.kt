package ru.profitsw2000.mvpapp.ui.login

import ru.profitsw2000.mvpapp.domain.LoginUseCase
import ru.profitsw2000.mvpapp.utils.Publisher

private const val ERROR_SIGN_IN = 1
private const val ERROR_PASSWORD_RESTORE = 2
private const val ERROR_SIGN_UP = 3
private const val ERROR_EMPTY_FIELD = 4

class LoginViewModel(private val loginUseCase: LoginUseCase): LoginContract.ViewModel {
    override val showProgress: Publisher<Boolean> = Publisher()
    override val isSignInSuccess: Publisher<Boolean> = Publisher()
    override val isRestorePasswordSuccess: Publisher<Boolean> = Publisher()
    override val isSignUpSuccess: Publisher<Boolean> = Publisher()
    override val errorCode: Publisher<Int?> = Publisher()

    override fun onLogin(login: String, password: String) {
        if (login.isNotEmpty() && password.isNotEmpty()) {
            showProgress.post(true)
            loginUseCase.login(login, password) { result ->
                if (result) {
                    isSignInSuccess.post(true)
                } else {
                    errorCode.post(ERROR_SIGN_IN)
                }
                showProgress.post(false)
            }
        } else {errorCode.post(ERROR_EMPTY_FIELD)}
    }

    override fun onRestorePassword(email: String) {
        if (email.isNotEmpty()) {
            showProgress.post(true)
            loginUseCase.restorePassword(email) { result ->
                if (result) isRestorePasswordSuccess.post(true)
                else errorCode.post(ERROR_PASSWORD_RESTORE)
                showProgress.post(false)
            }
        } else {errorCode.post(ERROR_EMPTY_FIELD)}
    }

    override fun onSignUp(email: String, login: String, password: String) {
        if (email.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()) {
            showProgress.post(true)
            loginUseCase.register(login, password, email) { result ->
                if(result) isSignUpSuccess.post(true)
                else errorCode.post(ERROR_SIGN_UP)
                showProgress.post(false)
            }
        } else {errorCode.post(ERROR_EMPTY_FIELD)}
    }

}