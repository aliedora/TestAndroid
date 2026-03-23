package com.example.testandroid.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.testandroid.MainActivity
import com.example.testandroid.data.network.ApiService
import com.example.testandroid.screen.HomeScreen
import com.example.testandroid.util.RetrofitTestHelper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.http.Fault
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Before
import org.junit.BeforeClass
import org.junit.AfterClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Feature("API Button")
@RunWith(AndroidJUnit4::class)
class ApiButtonTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val homeScreen = HomeScreen()

    companion object {
        private val wireMockServer = WireMockServer(wireMockConfig().port(8080))

        @BeforeClass
        @JvmStatic
        fun startServer() {
            wireMockServer.start()
            val testApiService = Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
            RetrofitTestHelper.inject(testApiService)
        }

        @AfterClass
        @JvmStatic
        fun stopServer() {
            wireMockServer.stop()
        }
    }

    @Before
    fun setUp() {
        wireMockServer.resetAll()
    }

    @Story("API button is visible")
    @Test
    fun testApiButtonIsVisible() {
        homeScreen.assertCallApiButtonVisible()
    }

    @Story("200 OK response shows title and body")
    @Test
    fun testSuccessResponseDisplayed() {
        wireMockServer.stubFor(
            get(urlEqualTo("/posts/1")).willReturn(
                aResponse().withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("""{"id":1,"userId":1,"title":"Sample Title","body":"Sample Body"}""")
            )
        )
        homeScreen.clickCallApi()
        homeScreen.assertApiResultContains("Sample Title")
        homeScreen.assertApiResultContains("Sample Body")
    }

    @Story("404 error shows error message")
    @Test
    fun testHttp404ErrorDisplayed() {
        wireMockServer.stubFor(
            get(urlEqualTo("/posts/1")).willReturn(aResponse().withStatus(404))
        )
        homeScreen.clickCallApi()
        homeScreen.assertApiResultContains("Error:")
        homeScreen.assertApiResultContains("404")
    }

    @Story("503 error shows error message")
    @Test
    fun testHttp503ErrorDisplayed() {
        wireMockServer.stubFor(
            get(urlEqualTo("/posts/1")).willReturn(aResponse().withStatus(503))
        )
        homeScreen.clickCallApi()
        homeScreen.assertApiResultContains("Error:")
        homeScreen.assertApiResultContains("503")
    }

    @Story("Network error shows error message")
    @Test
    fun testNetworkErrorDisplayed() {
        wireMockServer.stubFor(
            get(urlEqualTo("/posts/1")).willReturn(
                aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)
            )
        )
        homeScreen.clickCallApi()
        homeScreen.assertApiResultContains("Error:")
    }
}
