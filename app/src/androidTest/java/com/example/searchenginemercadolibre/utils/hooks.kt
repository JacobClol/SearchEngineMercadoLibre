package com.example.searchenginemercadolibre.utils

import androidx.test.espresso.AmbiguousViewMatcherException
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Assert


fun isTextDisplayed(text: String?) {
    var isDisplayed = true
    Espresso.onView(ViewMatchers.withSubstring(text))
        .withFailureHandler { error, _ ->
            isDisplayed = error is AmbiguousViewMatcherException
        }
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    Assert.assertTrue(isDisplayed)
}
