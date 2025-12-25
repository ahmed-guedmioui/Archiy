import com.build_logic.configureKotlinAndroid
import com.android.build.api.dsl.LibraryExtension
import com.build_logic.configureBuildFields
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    buildConfig = true
                    compose = true
                }

                configureKotlinAndroid(this)

                configureBuildFields(this)
            }
        }
    }
}