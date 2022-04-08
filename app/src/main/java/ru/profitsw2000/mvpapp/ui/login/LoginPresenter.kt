package ru.profitsw2000.mvpapp.ui.login

import ru.profitsw2000.mvpapp.domain.LoginUseCase
import ru.profitsw2000.mvpapp.ui.ERROR_EMPTY_FIELD
import ru.profitsw2000.mvpapp.ui.ERROR_PASSWORD_RESTORE
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_IN
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_UP

class LoginPresenter(private val loginUseCase: LoginUseCase): LoginContract.Presenter {

    private var view: LoginContract.View? = null

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {

        view?.showProgress()

        loginUseCase.login(login, password) {result ->
            if (result) {
                view?.setSignInSuccess()
            } else {
                view?.setError(ERROR_SIGN_IN)
            }
            view?.hideProgress()
        }
    }

    override fun onRestorePassword(email: String) {
        if (email.isNotEmpty()) {
            view?.showProgress()
            loginUseCase.restorePassword(email) { result ->
                if (result) view?.setRestorePasswordSuccess()
                else view?.setError(ERROR_PASSWORD_RESTORE)
                view?.hideProgress()
            }
        } else {view?.setError(ERROR_EMPTY_FIELD)}
    }

    override fun onSignUp(email: String, login: String, password: String) {
        if (email.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()) {
            view?.showProgress()
            loginUseCase.register(login, password, email) { result ->
                if(result) view?.setSignUpSuccess()
                else view?.setError(ERROR_SIGN_UP)

                view?.hideProgress()
            }
        } else {view?.setError(ERROR_EMPTY_FIELD)}
    }
}