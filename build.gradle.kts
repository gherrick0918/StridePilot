// This is the build.gradle.kts file in your project's root directory, NOT the one in the app folder.
plugins {
    // It's important to specify the version here.
    // The error mentions 2.2.21, so we'll use that.
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) version "2.2.21" apply false
    alias(libs.plugins.kotlin.compose) version "2.2.21" apply false
}
