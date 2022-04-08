package ru.profitsw2000.mvpapp.ui.screens

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.profitsw2000.mvpapp.R
import ru.profitsw2000.mvpapp.app
import ru.profitsw2000.mvpapp.databinding.ActivityMainBinding
import ru.profitsw2000.mvpapp.databinding.ActivitySignUpBinding
import ru.profitsw2000.mvpapp.ui.ERROR_EMPTY_FIELD
import ru.profitsw2000.mvpapp.ui.ERROR_PASSWORD_RESTORE
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_IN
import ru.profitsw2000.mvpapp.ui.ERROR_SIGN_UP
import ru.profitsw2000.mvpapp.ui.login.LoginContract
import ru.profitsw2000.mvpapp.ui.login.LoginPresenter

class SignUpActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivitySignUpBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.signUpScreenSignUpButton.setOnClickListener {
            with(binding){
                presenter?.onSignUp(
                    signUpScreenEmailEditText.text.toString(),
                    signUpScreenLoginEditText.text.toString(),
                    signUpScreenPasswordEditText.text.toString())
            }
        }
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSignInSuccess() {
    }

    override fun setRestorePasswordSuccess() {
    }

    override fun setSignUpSuccess() {
        binding.signUpScreenMainGroup.visibility = View.GONE
        binding.tvSignUpSuccesful.visibility = View.VISIBLE
    }

    override fun setError(errorNumber: Int) {
        when(errorNumber){
            ERROR_SIGN_UP -> showDialog(getString(R.string.dialog_registration_error_title), getString(R.string.dialog_registration_error_text))
            ERROR_EMPTY_FIELD -> showDialog(getString(R.string.dialog_empty_field_error_title), getString(R.string.dialog_empty_field_error_text))
            else -> {}
        }
    }

    override fun showProgress() {
        with(binding) {
            signUpScreenMainGroup.visibility = View.GONE
            tvSignUpSuccesful.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        with(binding) {
            progressBar.visibility = View.GONE
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showDialog(title: String, message: String) {
        this?.let {
            AlertDialog.Builder(it)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(getString(R.string.dialog_button_ok_text)){
                        dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }
}