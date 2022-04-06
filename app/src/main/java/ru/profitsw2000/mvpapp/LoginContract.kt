package ru.profitsw2000.mvpapp

import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setSignInSuccess()
        @MainThread
        fun setRestorePasswordSuccess()
        @MainThread
        fun setError(errorNumber: Int)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onRestorePassword(login: String)
    }
}