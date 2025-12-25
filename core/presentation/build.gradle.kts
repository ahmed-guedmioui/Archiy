plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.core.presentation"
}

dependencies {
    // Core domain
    implementation(projects.core.domain)

    // Androidx
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    api(libs.bundles.compose)
    api(platform(libs.androidx.compose.bom))

    // Kotlinx serialization json
    api(libs.kotlinx.serialization.json)

    // Koin
    api(libs.bundles.koin)
    
    // Lifecycle
    api(libs.androidx.lifecycle.process)

    // Coil
    api(libs.coil.compose)

    // Extended Icons
    api(libs.androidx.material.icons.extended)
}