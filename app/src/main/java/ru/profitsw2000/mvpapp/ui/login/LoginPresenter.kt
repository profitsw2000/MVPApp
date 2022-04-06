package ru.profitsw2000.mvpapp.ui.login

import android.os.Handler
import android.os.Looper
import ru.profitsw2000.mvpapp.data.TestLoginApiImpl
import ru.profitsw2000.mvpapp.ui.ERROR_EMPTY_FIELD
import ru.profitsw2000.mvpapp.ui.ERROR_PASSWORD_RESTORE
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_IN
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_UP

class LoginPresenter(private val loginApi: TestLoginApiImpl): LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {

        view?.showProgress()
        Thread {
            Thread.sleep(3_000)
            uiHandler.post {
                if (loginApi.login(login,password)) {
                    view?.setSignInSuccess()
                } else {
                    view?.setError(ERROR_SIGN_IN)
                }
                view?.hideProgress()
            }
        }.start()
    }

    override fun onRestorePassword(login: String) {
        view?.showProgress()
        Thread {
            Thread.sleep(2_000)
            uiHandler.post {
                if (loginApi.restorePassword(login)) view?.setRestorePasswordSuccess()
                else view?.setError(ERROR_PASSWORD_RESTORE)
                view?.hideProgress()
            }
        }.start()
    }

    override fun onSignUp(login: String, password: String) {
        view?.showProgress()
        Thread {
            Thread.sleep(4_000)
            uiHandler.post {
                if (login.isNotEmpty() && password.isNotEmpty()) {
                    if(loginApi.register(login,password)) view?.setSignUpSuccess()
                    else view?.setError(ERROR_SIGN_UP)
                } else view?.setError(ERROR_EMPTY_FIELD)
                view?.hideProgress()
            }
        }.start()
    }
}