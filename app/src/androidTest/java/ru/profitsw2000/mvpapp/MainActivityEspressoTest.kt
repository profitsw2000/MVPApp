package ru.profitsw2000.mvpapp

import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.profitsw2000.mvpapp.ui.login.MainActivity

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun loginEditText_IsDisplayed() {
        Espresso.onView(withId(R.id.login_edit_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun passwordEditText_IsDisplayed() {
        Espresso.onView(withId(R.id.password_edit_text))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun forgotPasswordTextView_NotNull() {
        scenario.onActivity {
            val forgotPasswordTextView = it.findViewById<TextView>(R.id.tv_forgot_password)
            TestCase.assertNotNull(forgotPasswordTextView)
        }
    }

    @Test
    fun forgotPasswordTextView_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(R.string.forgot_password_text))
        Espresso.onView(withId(R.id.tv_forgot_password)).check(assertion)
    }

    @Test
    fun forgotPasswordTextView_IsDisplayed() {
        Espresso.onView(withId(R.id.tv_forgot_password))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signUpTextView_NotNull() {
        scenario.onActivity {
            val signUpTextView = it.findViewById<TextView>(R.id.tv_sign_up)
            TestCase.assertNotNull(signUpTextView)
        }
    }

    @Test
    fun signUpTextView_HasText() {
        val assertion = ViewAssertions.matches(ViewMatchers.withText(R.string.sign_up_text))
        Espresso.onView(withId(R.id.tv_sign_up)).check(assertion)
    }

    @Test
    fun signUpTextView_IsDisplayed() {
        Espresso.onView(withId(R.id.tv_sign_up))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun buttonSignIn_IsEffectiveVisible() {
        Espresso.onView(withId(R.id.sign_in_button))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun progressBar_IsNotVisible(){
        Espresso.onView(withId(R.id.progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    @After
    fun close() {
        scenario.close()
    }
}