package br.hthenrique.mygoalskotlin.UI.Splash.View


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import br.hthenrique.mygoalskotlin.R
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun loginActivityTest() {
        val editText = onView(
            allOf(
                withId(R.id.editTextEmail), withText("henrique@gmail.com"),
                withParent(
                    allOf(
                        withId(R.id.constraintLayout3),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        editText.check(matches(withText("henrique@gmail.com")))

        val editText2 = onView(
            allOf(
                withId(R.id.editTextPassword), withText("Henrique#3"),
                withParent(
                    allOf(
                        withId(R.id.constraintLayout3),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("Henrique#3")))

        val button = onView(
            allOf(
                withId(R.id.buttonLogin), withText("LOGIN"),
                withParent(
                    allOf(
                        withId(R.id.constraintLayout3),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.buttonRegister), withText("REGISTER"),
                withParent(
                    allOf(
                        withId(R.id.constraintLayout3),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))
    }
}
