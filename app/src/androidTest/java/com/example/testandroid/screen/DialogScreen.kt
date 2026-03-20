package com.example.testandroid.screen

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.testandroid.R
import io.qameta.allure.kotlin.Step

class DialogScreen {
    private val title = onView(withText(R.string.dialog_title))
    private val message = onView(withText(R.string.dialog_message))
    private val okButton = onView(withText(android.R.string.ok))
    private val cancelButton = onView(withText(android.R.string.cancel))
    
    @Step("Verify dialog")
    fun checkDialogComponents() {
        assertTitleVisible()
        assertMessageVisible()
        assertOkButtonVisible()
        assertCancelButtonVisible()
    }
    private fun assertTitleVisible() = title.check(matches(isDisplayed()))

    private fun assertMessageVisible() = message.check(matches(isDisplayed()))

    private fun assertOkButtonVisible() = okButton.check(matches(isDisplayed()))

    private fun assertCancelButtonVisible() = cancelButton.check(matches(isDisplayed()))

    @Step("Click OK button")
    fun clickOk() = okButton.perform(click())

    @Step("Click Cancel button")
    fun clickCancel() = cancelButton.perform(click())

    @Step("Verify dialog is dismissed")
    fun assertDismissed() = title.check(doesNotExist())
}
