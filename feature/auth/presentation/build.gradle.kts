plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.auth.presentation"
}

dependencies {
    // Nav api
    implementation(projects.navRoot.api)

    // Core domain
    implementation(projects.core.domain)

    // Core presentation
    implementation(projects.core.presentation)

    // Auth domain
    implementation(projects.feature.auth.domain)
}