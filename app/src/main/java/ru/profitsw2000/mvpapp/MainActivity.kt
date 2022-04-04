package ru.profitsw2000.mvpapp

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    override fun setSuccess() {
        with(binding) {
            mainGroup.visibility = View.GONE
            tvLoginSuccesful.visibility = View.VISIBLE
            root.setBackgroundColor(Color.GREEN)
        }
    }

    override fun setError(error: String) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
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
}