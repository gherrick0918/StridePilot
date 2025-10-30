import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // You have this alias in your version catalog, but you used "apply true" before.
    // The convention is to apply the plugin directly.
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "io.github.gherrick0918.stridepilot"
    compileSdk = 36

    defaultConfig {
        applicationId = "io.github.gherrick0918.stridepilot"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21) // Or your desired JVM version
        }
    }
    buildFeatures {
        compose = true
    }
    // The composeOptions block explicitly defines the compiler version.
    // This should match your Kotlin plugin version.
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14" // This version is compatible with Kotlin 2.0.21
    }
}

dependencies {
    // Glance for App Widgets
    implementation(libs.glance.appwidget)
    implementation(libs.glance.material3)

    // Core and Compose dependencies
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.m3)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
