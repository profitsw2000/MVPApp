package ru.profitsw2000.mvpapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import ru.profitsw2000.mvpapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.signInButton.setOnClickListener {
            presenter?.onLogin(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString())
        }

        binding.tvForgotPassword.setOnClickListener {
            presenter?.onRestorePassword(binding.loginEditText.text.toString())
        }

        binding.tvSignUp.setOnClickListener {
            presenter?.onSignUp(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString())
        }
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter(loginApi = TestLoginApiImpl())
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSignInSuccess() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun setRestorePasswordSuccess() {
        this.showDialog(getString(R.string.dialog_restore_password_success_title), getString(R.string.dialog_restore_password_success_text))
    }

    override fun setSignUpSuccess() {
        this.showDialog(getString(R.string.dialog_sign_up_success_title), getString(R.string.dialog_sign_up_success_text))
    }

    override fun setError(errorNumber: Int) {
        when(errorNumber){
            1 -> showDialog(getString(R.string.dialog_sign_in_error_title), getString(R.string.dialog_sign_in_error_text))
            2 -> showDialog(getString(R.string.dialog_restore_password_error_title), getString(R.string.dialog_restore_password_error_text))
            3 -> showDialog(getString(R.string.dialog_registration_error_title), getString(R.string.dialog_registration_error_text))
            4 -> showDialog(getString(R.string.dialog_empty_field_error_title), getString(R.string.dialog_empty_field_error_text))
            else -> {}
        }
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.signInButton.isEnabled = false
        setAlpha(0.1f)
        hideKeyboard(this)
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
        binding.signInButton.isEnabled = true
        setAlpha(1f)
    }

    private fun setAlpha(alpha: Float){
        with(binding){
            loginEditText.alpha = alpha
            passwordEditText.alpha = alpha
            tvForgotPassword.alpha = alpha
            tvSignUp.alpha = alpha
            signInButton.alpha = alpha
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