plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.home.presentation"
}

dependencies {
    // Nav api
    implementation(projects.navRoot.api)

    // Core domain
    implementation(projects.core.domain)

    // Core presentation
    implementation(projects.core.presentation)

    // Home domain
    implementation(projects.feature.home.domain)
}