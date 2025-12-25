plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.auth.data"
}

dependencies {
    // Core data
    implementation(projects.core.data)

    // Core domain
    implementation(projects.core.domain)

    // Auth domain
    implementation(projects.feature.auth.domain)
}