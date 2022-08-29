package ru.profitsw2000.mvpapp.ui.login


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.profitsw2000.mvpapp.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityViewsSimpleTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityViewsSimpleTest() {
        val button = onView(
            allOf(
                withId(R.id.sign_in_button), withText("SIGN IN"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tv_forgot_password), withText("Forgot password?"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Forgot password?")))

        val textView2 = onView(
            allOf(
                withId(R.id.tv_forgot_password), withText("Forgot password?"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView2.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.tv_sign_up), withText("Sign Up"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Sign Up")))

        val textView4 = onView(
            allOf(
                withId(R.id.tv_sign_up), withText("Sign Up"),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        textView4.check(matches(isDisplayed()))

        val editText = onView(
            allOf(
                withId(R.id.login_edit_text),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText.check(matches(isDisplayed()))

        val editText2 = onView(
            allOf(
                withId(R.id.password_edit_text),
                withParent(withParent(withId(android.R.id.content))),
                isDisplayed()
            )
        )
        editText2.check(matches(isDisplayed()))
    }
}
