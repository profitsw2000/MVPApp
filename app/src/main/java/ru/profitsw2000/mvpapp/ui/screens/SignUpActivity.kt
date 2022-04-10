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
import ru.profitsw2000.mvpapp.databinding.ActivitySignUpBinding
import ru.profitsw2000.mvpapp.ui.login.LoginContract
import ru.profitsw2000.mvpapp.ui.login.LoginViewModel

private const val ERROR_SIGN_UP = 3
private const val ERROR_EMPTY_FIELD = 4

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }
    //private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()
/*        presenter = restorePresenter()
        presenter?.onAttach(this)*/

        binding.signUpScreenSignUpButton.setOnClickListener {
            with(binding){
                viewModel?.onSignUp(
                    signUpScreenEmailEditText.text.toString(),
                    signUpScreenLoginEditText.text.toString(),
                    signUpScreenPasswordEditText.text.toString())
            }
        }

        viewModel?.isSignUpSuccess?.subscribe(handler) {
            if(it == true) {
                setSignUpSuccess()
            }
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
    }

    private fun restoreViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(app.loginUseCase)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

/*    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(app.loginUseCase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }*/

/*    override fun setSignInSuccess() {
    }

    override fun setRestorePasswordSuccess() {
    }*/

    private fun setSignUpSuccess() {
        binding.signUpScreenMainGroup.visibility = View.GONE
        binding.tvSignUpSuccesful.visibility = View.VISIBLE
    }

    private fun setError(errorNumber: Int) {
        when(errorNumber){
            ERROR_SIGN_UP -> showDialog(getString(R.string.dialog_registration_error_title), getString(R.string.dialog_registration_error_text))
            ERROR_EMPTY_FIELD -> showDialog(getString(R.string.dialog_empty_field_error_title), getString(R.string.dialog_empty_field_error_text))
            else -> {}
        }
        binding.signUpScreenMainGroup.visibility = View.VISIBLE
    }

    private fun showProgress() {
        with(binding) {
            signUpScreenMainGroup.visibility = View.GONE
            tvSignUpSuccesful.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
        hideKeyboard(this)
    }

    private fun hideProgress() {
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