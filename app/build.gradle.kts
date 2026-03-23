plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.testandroid"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.testandroid"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.testandroid.runner.AllureInstrumentationRunner"
        testInstrumentationRunnerArguments["allureResultsDirectory"] =
            "/data/data/com.example.testandroid/files/allure-results"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/DEPENDENCIES",
                "META-INF/io.netty.versions.properties",
                "META-INF/INDEX.LIST"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.appcompat)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.androidx.fragment.testing)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.allure.kotlin.android)
    androidTestImplementation(libs.allure.kotlin.commons)
    androidTestImplementation(libs.wiremock) {
        exclude(group = "org.hamcrest")
    }
}

tasks.register("pullAllureResults") {
    doLast {
        val adb = "${android.sdkDirectory}/platform-tools/adb"
        val dest = rootProject.file("allure-results")
        dest.deleteRecursively()
        dest.mkdirs()
        exec {
            commandLine(adb, "pull", "/sdcard/Download/allure-results/.", dest.absolutePath)
            isIgnoreExitValue = true
        }
    }
}

tasks.matching { it.name.startsWith("connected") && it.name.endsWith("AndroidTest") }
    .configureEach {
        finalizedBy("pullAllureResults")
    }
