package ru.profitsw2000.mvpapp.ui.screens

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.profitsw2000.mvpapp.R
import ru.profitsw2000.mvpapp.app
import ru.profitsw2000.mvpapp.databinding.ActivityForgotPasswordBinding
import ru.profitsw2000.mvpapp.ui.login.LoginContract
import ru.profitsw2000.mvpapp.ui.login.LoginPresenter

private const val ERROR_PASSWORD_RESTORE = 2
private const val ERROR_EMPTY_FIELD = 4

class ForgotPasswordActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityForgotPasswordBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.forgotPasswordButton.setOnClickListener {
            presenter?.onRestorePassword(binding.forgotPasswordEmailEditText.text.toString())
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
        with(binding){
            forgotPasswordScreenMainGroup.visibility = View.GONE
            tvRestorePasswordSuccesful.visibility = View.GONE
            binding.tvRestorePasswordSuccesful.visibility = View.VISIBLE
        }
    }

    override fun setSignUpSuccess() {
    }

    override fun setError(errorNumber: Int) {
        when(errorNumber){
            ERROR_PASSWORD_RESTORE -> showDialog(getString(R.string.dialog_restore_password_error_title), getString(R.string.dialog_restore_password_error_text))
            ERROR_EMPTY_FIELD -> showDialog(getString(R.string.dialog_empty_field_error_title), getString(R.string.dialog_empty_field_error_text))
            else -> {}
        }
        binding.forgotPasswordScreenMainGroup.visibility = View.VISIBLE
        binding.forgotPasswordEmailEditText.getText().clear()
    }

    override fun showProgress() {
        with(binding) {
            forgotPasswordScreenMainGroup.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
        hideKeyboard(this)
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
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