package ru.profitsw2000.mvpapp

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import ru.profitsw2000.mvpapp.ui.login.LoginContract
import ru.profitsw2000.mvpapp.ui.login.MainActivity


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>
    private lateinit var context: Context
    @Mock
    private lateinit var presenter: LoginContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        scenario = ActivityScenario.launch(MainActivity::class.java)
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            Assert.assertNotNull(it)
        }
    }

    @Test
    fun activity_isResumed() {
        Assert.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    //progressBar
    @Test
    fun progressBar_IsInvisible() {
        scenario.onActivity {
            val progressBar = it.findViewById<ProgressBar>(R.id.progress_bar)
            Assert.assertEquals(View.GONE, progressBar.visibility)
        }
    }

    //проверка Login EditText
    @Test
    fun loginEditText_isVisible() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.login_edit_text)
            Assert.assertEquals(View.VISIBLE, editText.visibility)
        }
    }

    @Test
    fun loginEditText_isTypedText() {
        scenario.onActivity {
            val text = "test"
            val editText = it.findViewById<EditText>(R.id.login_edit_text)
            editText.setText(text, TextView.BufferType.EDITABLE)
            Assert.assertEquals(text, editText.text.toString())
        }
    }

    //проверка Password EditText
    @Test
    fun passwordEditText_isVisible() {
        scenario.onActivity {
            val editText = it.findViewById<EditText>(R.id.password_edit_text)
            Assert.assertEquals(View.VISIBLE, editText.visibility)
        }
    }

    @Test
    fun passwordEditText_isTypedText() {
        scenario.onActivity {
            val text = "test"
            val editText = it.findViewById<EditText>(R.id.password_edit_text)
            editText.setText(text, TextView.BufferType.EDITABLE)
            Assert.assertEquals(text, editText.text.toString())
        }
    }

    //тесты кнопки
    @Test
    fun signInButton_isVisible() {
        scenario.onActivity {
            val button = it.findViewById<Button>(R.id.sign_in_button)
            Assert.assertEquals(View.VISIBLE, button.visibility)
        }
    }

    //проверка ForgotPassword TextView
    @Test
    fun forgotPasswordTextView_NotNull() {
        scenario.onActivity {
            val forgotPasswordTextView = it.findViewById<TextView>(R.id.tv_forgot_password)
            TestCase.assertNotNull(forgotPasswordTextView)
        }
    }

    @Test
    fun forgotPasswordTextView_HasText() {
        scenario.onActivity {
            val forgotPasswordTextView = it.findViewById<TextView>(R.id.tv_forgot_password)
            TestCase.assertEquals("Forgot password?", forgotPasswordTextView.text)
        }
    }

    @Test
    fun forgotPasswordTextView_IsVisible() {
        scenario.onActivity {
            val forgotPasswordTextView = it.findViewById<TextView>(R.id.tv_forgot_password)
            TestCase.assertEquals(View.VISIBLE, forgotPasswordTextView.visibility)
        }
    }

    //проверка SignUp TextView
    @Test
    fun signUpTextView_NotNull() {
        scenario.onActivity {
            val signUpTextView = it.findViewById<TextView>(R.id.tv_sign_up)
            TestCase.assertNotNull(signUpTextView)
        }
    }

    @Test
    fun signUpTextView_HasText() {
        scenario.onActivity {
            val signUpTextView = it.findViewById<TextView>(R.id.tv_sign_up)
            TestCase.assertEquals("Sign Up", signUpTextView.text)
        }
    }

    @Test
    fun signUpTextView_IsVisible() {
        scenario.onActivity {
            val signUpTextView = it.findViewById<TextView>(R.id.tv_sign_up)
            TestCase.assertEquals(View.VISIBLE, signUpTextView.visibility)
        }
    }

    @After
    fun close() {
        MockitoAnnotations.openMocks(this).close()
        scenario.close()
    }
}