package ru.profitsw2000.mvpapp

import android.os.Handler
import android.os.Looper

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
                view?.hideProgress()
                if (loginApi.login(login,password)) {
                    view?.setSignInSuccess()
                } else {
                    view?.setError(1)
                }
            }
        }.start()
    }

    override fun onRestorePassword(login: String) {
        view?.showProgress()
        Thread {
            Thread.sleep(2_000)
            uiHandler.post {
                view?.hideProgress()
                if (loginApi.restorePassword(login)) view?.setRestorePasswordSuccess()
                else view?.setError(2)
            }
        }.start()
    }
}