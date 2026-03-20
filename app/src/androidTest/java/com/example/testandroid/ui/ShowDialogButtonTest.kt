package com.example.testandroid.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testandroid.MainActivity
import com.example.testandroid.screen.DialogScreen
import com.example.testandroid.screen.HomeScreen
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Feature("Show Dialog Button")
@RunWith(AndroidJUnit4::class)
class ShowDialogButtonTest {
    @get:Rule val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val homeScreen = HomeScreen()
    private val dialogScreen = DialogScreen()

    @Story("Dialog button is visible with correct label")
    @Test
    fun testShouldDisplayDialogButton() {
        homeScreen.assertShowDialogButtonVisible()
    }

    @Story("Dialog shows title, message, OK and Cancel")
    @Test
    fun testShouldOpenDialogWithAllComponents() {
        homeScreen.clickShowDialog()
        dialogScreen.checkDialogComponents()
    }

    @Story("Dialog closes on OK click")
    @Test
    fun testShouldCloseDialogByOkClick() {
        homeScreen.clickShowDialog()
        dialogScreen.clickOk()
        dialogScreen.assertDismissed()
    }

    @Story("Dialog closes on Cancel click")
    @Test
    fun testShouldCloseDialogByCancelClick() {
        homeScreen.clickShowDialog()
        dialogScreen.clickCancel()
        dialogScreen.assertDismissed()
    }
}
