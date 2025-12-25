plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.home.presentation"
}

dependencies {
    // Core domain
    implementation(projects.core.domain)

    // Core presentation
    implementation(projects.core.presentation)

    // Home domain
    implementation(projects.feature.home.domain)

    // Home data
    implementation(projects.feature.home.data)
}