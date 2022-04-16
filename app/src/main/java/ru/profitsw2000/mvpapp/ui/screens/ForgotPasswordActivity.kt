@file:Suppress("DEPRECATION")

package ru.profitsw2000.mvpapp.ui.screens

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.profitsw2000.mvpapp.R
import ru.profitsw2000.mvpapp.app
import ru.profitsw2000.mvpapp.databinding.ActivityForgotPasswordBinding
import ru.profitsw2000.mvpapp.ui.login.LoginViewModel
import ru.profitsw2000.mvpapp.ui.login.ViewModel

private const val ERROR_PASSWORD_RESTORE = 2
private const val ERROR_EMPTY_FIELD = 4

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private var viewModel: ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()

        binding.forgotPasswordButton.setOnClickListener {
            viewModel?.onRestorePassword(binding.forgotPasswordEmailEditText.text.toString())
        }

        viewModel?.showProgress?.subscribe(handler) {
            if (it == true) {
                showProgress()
            } else {
                hideProgress()
            }
        }

        viewModel?.errorCode?.subscribe(handler){
            setError(it!!)
        }

        viewModel?.isRestorePasswordSuccess?.subscribe(handler) {
            if(it == true) {
                setRestorePasswordSuccess()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isRestorePasswordSuccess?.unsubscribeAll()
        viewModel?.showProgress?.unsubscribeAll()
        viewModel?.errorCode?.unsubscribeAll()
    }

    private fun restoreViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(app.loginUseCase)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    private fun setRestorePasswordSuccess() {
        with(binding){
            forgotPasswordScreenMainGroup.visibility = View.GONE
            restorePasswordSuccessfulTextView.visibility = View.GONE
            binding.restorePasswordSuccessfulTextView.visibility = View.VISIBLE
        }
    }

    private fun setError(errorNumber: Int) {
        when(errorNumber){
            ERROR_PASSWORD_RESTORE -> showDialog(getString(R.string.dialog_restore_password_error_title), getString(R.string.dialog_restore_password_error_text))
            ERROR_EMPTY_FIELD -> showDialog(getString(R.string.dialog_empty_field_error_title), getString(R.string.dialog_empty_field_error_text))
            else -> {}
        }
        binding.forgotPasswordScreenMainGroup.visibility = View.VISIBLE
        binding.forgotPasswordEmailEditText.text.clear()
    }

    private fun showProgress() {
        with(binding) {
            forgotPasswordScreenMainGroup.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
        hideKeyboard(this)
    }

    private fun hideProgress() {
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
        this.let {
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