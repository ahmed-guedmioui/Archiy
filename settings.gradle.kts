pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()

        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Archiy"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:presentation")
include(":core:data")
include(":core:domain")
include("feature:auth:presentation")
include("feature:auth:domain")
include("feature:auth:data")
include(":nav-root:api")
include(":nav-root:impl")
include(":feature:home:presentation")
include(":feature:home:domain")
include(":feature:home:data")
include(":feature:profile:presentation")
include(":feature:profile:data")
include(":feature:profile:domain")
