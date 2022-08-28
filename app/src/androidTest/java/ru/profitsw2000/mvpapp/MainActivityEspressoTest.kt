package ru.profitsw2000.mvpapp

import android.content.Intent
import android.provider.Contacts
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.profitsw2000.mvpapp.ui.login.MainActivity
import ru.profitsw2000.mvpapp.ui.screens.AccountActivity
import ru.profitsw2000.mvpapp.ui.screens.ForgotPasswordActivity
import ru.profitsw2000.mvpapp.ui.screens.SignUpActivity

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)
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
        val text = "Forgot password?"
        val assertion = ViewAssertions.matches(ViewMatchers.withText(text))
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
        val text = "Sign Up"
        val assertion = ViewAssertions.matches(ViewMatchers.withText(text))
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
    fun buttonSignIn_hasText() {
        val buttonText = "Sign In"
        Espresso.onView(withId(R.id.sign_in_button))
            .check(ViewAssertions.matches(ViewMatchers.withText(buttonText)))
    }

    @Test
    fun progressBar_IsNotVisible(){
        Espresso.onView(withId(R.id.progress_bar))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

    //проверка запуска активити с аккаунтом при введении верного логина и пароля
    @Test
    fun signInFunction_IsWorking() {
        Espresso.onView(withId(R.id.login_edit_text)).perform(click())
        Espresso.onView(withId(R.id.login_edit_text)).perform(replaceText("admin"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.password_edit_text)).perform(click())
        Espresso.onView(withId(R.id.password_edit_text)).perform(replaceText("1234"), closeSoftKeyboard())

        Espresso.onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.onView(ViewMatchers.isRoot()).perform(delay(5000))
        intended(hasComponent(AccountActivity::class.java.name))
    }

    //проверка запуска диалога при введении неверного пароля или логина
    @Test
    fun invalidLoginPasswordDialog_IsWorking() {
        Espresso.onView(withId(R.id.login_edit_text)).perform(click())
        Espresso.onView(withId(R.id.login_edit_text)).perform(replaceText("admin"), closeSoftKeyboard())
        Espresso.onView(withId(R.id.password_edit_text)).perform(click())
        Espresso.onView(withId(R.id.password_edit_text)).perform(replaceText("123"), closeSoftKeyboard())

        Espresso.onView(withId(R.id.sign_in_button)).perform(click())
        Espresso.onView(ViewMatchers.isRoot()).perform(delay(5000))
        Espresso.onView(withText(R.string.dialog_sign_in_error_text)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withText(R.string.dialog_button_ok_text)).perform(click())
    }

    //проверка запуска активити с формой для регистрации
    @Test
    fun signUpActivity_IsLaunched() {
        Espresso.onView(withId(R.id.tv_sign_up)).perform(click())

        Espresso.onView(ViewMatchers.isRoot()).perform(delay(5000))
        intended(hasComponent(SignUpActivity::class.java.name))
    }

    //проверка запуска активити с формой для восстановления пароля
    @Test
    fun forgotPasswordActivity_IsLaunched() {
        Espresso.onView(withId(R.id.tv_forgot_password)).perform(click())

        Espresso.onView(ViewMatchers.isRoot()).perform(delay(5000))
        intended(hasComponent(ForgotPasswordActivity::class.java.name))
    }

    private fun delay(delayTime: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delayTime seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delayTime)
            }
        }
    }

    @After
    fun close() {
        scenario.close()
    }
}