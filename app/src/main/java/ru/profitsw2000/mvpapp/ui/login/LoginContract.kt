package ru.profitsw2000.mvpapp.ui.login

import androidx.annotation.MainThread
import ru.profitsw2000.mvpapp.utils.Publisher

class LoginContract {

    interface View {
        @MainThread
        fun setSignInSuccess()
        @MainThread
        fun setRestorePasswordSuccess()
        @MainThread
        fun setSignUpSuccess()
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
        fun onRestorePassword(email: String)
        fun onSignUp(email: String, login: String, password: String)
    }

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
}