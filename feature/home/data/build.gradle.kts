plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.home.data"
}

dependencies {
    // Core data
    implementation(projects.core.data)

    // Core domain
    implementation(projects.core.domain)

    // Home domain
    implementation(projects.feature.home.domain)
}