package ru.profitsw2000.mvpapp

import android.os.Handler
import android.os.Looper

class LoginPresenter: LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var isSuccess: Boolean = false
    private var errorText: String = ""

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        } else {
            view.setError(errorText)
        }
    }

    override fun onLogin(login: String, password: String) {

        view?.showProgress()
        Thread {
            Thread.sleep(3_000)
            uiHandler.post {
                view?.hideProgress()
                if (login.contentEquals("admin") && password.contentEquals("1234")) {
                    view?.setSuccess()
                    isSuccess = true
                    errorText = ""
                } else {
                    view?.setError("Неверный пароль!!!")
                    isSuccess = false
                    errorText = "Неверный пароль!!!"
                }
            }
        }.start()
    }
}