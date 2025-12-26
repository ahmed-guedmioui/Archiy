plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.profile.data"
}

dependencies {
    // Core data
    implementation(projects.core.data)

    // Core domain
    implementation(projects.core.domain)

    // Profile domain
    implementation(projects.feature.profile.domain)
}