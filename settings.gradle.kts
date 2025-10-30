// This file likely exists in your project's root directory.
// If it doesn't, you may be using an older Gradle structure.
// In that case, apply the changes to your root build.gradle.kts instead.

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "StridePilot"
include(":app")

