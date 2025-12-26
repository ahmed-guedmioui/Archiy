plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.nav_root.impl"
}

dependencies {
    // Nav api
    implementation(projects.navRoot.api)

    // Core presentation
    implementation(projects.core.presentation)

    // Auth feature
    api(projects.feature.auth.data)
    api(projects.feature.auth.presentation)

    // Home feature
    api(projects.feature.home.data)
    api(projects.feature.home.presentation)

    // Profile feature
    api(projects.feature.profile.data)
    api(projects.feature.profile.presentation)
}