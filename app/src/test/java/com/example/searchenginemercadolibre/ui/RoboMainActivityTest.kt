package com.example.searchenginemercadolibre.ui

import android.widget.TextView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.searchenginemercadolibre.R
import com.example.searchenginemercadolibre.ui.fragment.SearchFragment
import com.example.searchenginemercadolibre.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
class RoboMainActivityTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() {
        activityScenarioRule.scenario
    }

    @Test
    fun fragmentSearch() {
       launchFragmentInHiltContainer<SearchFragment>(){
            assert(
                this.view?.findViewById<TextView>(R.id.tvLabelIntro)?.text.toString() == "Bienvenido al buscador de productos de Mercado Libre en Colombia"
            )
        }
    }
}