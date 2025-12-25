plugins {
    alias(libs.plugins.convention.android.library)
}

android {
    namespace = "com.core.data"
}

dependencies {
    // Core domain
    implementation(projects.core.domain)

    // Firebase
    api(libs.bundles.firebase)
    api(platform(libs.firebase.bom))

    // Koin
    api(libs.bundles.koin)

    // Kotlinx serialization json
    api(libs.kotlinx.serialization.json)

    // Ktor
    implementation(libs.bundles.ktor)

    // Data Store
    implementation(libs.androidx.datastore.preferences)
}