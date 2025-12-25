plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.nav_root.api"
}

dependencies {
    // Navigation 3
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.navigation3.runtime)
}