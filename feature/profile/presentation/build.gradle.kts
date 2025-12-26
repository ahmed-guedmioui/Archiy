plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.profile.presentation"
}

dependencies {
    // Nav api
    implementation(projects.navRoot.api)

    // Core domain
    implementation(projects.core.domain)

    // Core presentation
    implementation(projects.core.presentation)

    // Profile domain
    implementation(projects.feature.profile.domain)
}