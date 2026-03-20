package com.example.testandroid.screen

import android.widget.Toast
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.testandroid.R
import com.example.testandroid.helpers.ToastMatcher
import io.qameta.allure.kotlin.Step

class HomeScreen {
    private val showToastButton = onView(withId(R.id.btnShowToast))
    private val showDialogButton = onView(withId(R.id.btnShowDialog))

    @Step("Click Show Toast button")
    fun clickShowToast() = showToastButton.perform(click())

    @Step("Verify Show Toast button is displayed with correct label")
    fun assertShowToastButtonVisible() {
        showToastButton.check(matches(isDisplayed()))
        showToastButton.check(matches(withText(R.string.btn_show_toast)))
    }

    @Step("Verify toast message is displayed")
    fun assertToastMessageVisible() {
        onView(withText(R.string.toast_message))
         .inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Step("Click Show Dialog button")
    fun clickShowDialog() = showDialogButton.perform(click())

    @Step("Verify Show Dialog button is displayed with correct label")
    fun assertShowDialogButtonVisible() {
        showDialogButton.check(matches(isDisplayed()))
        showDialogButton.check(matches(withText(R.string.btn_show_dialog)))
    }
}