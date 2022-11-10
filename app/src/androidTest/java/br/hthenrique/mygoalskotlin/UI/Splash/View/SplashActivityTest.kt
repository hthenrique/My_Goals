package br.hthenrique.mygoalskotlin.UI.Splash.View

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SplashActivityTest{

    @get:Rule
    val activityTestRule: ActivityScenarioRule<SplashActivity>  = ActivityScenarioRule(SplashActivity::class.java)

    lateinit var scenario: ActivityScenario<SplashActivity>

    @Before
    fun setUp(){
        scenario = activityTestRule.scenario
        scenario.moveToState(Lifecycle.State.STARTED)
    }

}