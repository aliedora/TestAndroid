package com.example.testandroid.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testandroid.MainActivity
import com.example.testandroid.screen.HomeScreen
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Feature("Toast Button")
@RunWith(AndroidJUnit4::class)
class ShowToastButtonTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val homeScreen = HomeScreen()

    @Story("Toast button is visible with correct label")
    @Test
    fun testShouldDisplayToastButton() {
        homeScreen.assertShowToastButtonVisible()
    }

    @Story("Clicking button shows toast message")
    @Test
    fun testShouldShowToastOnButtonClick() {
        homeScreen.clickShowToast()
        homeScreen.assertToastMessageVisible()
    }
}
