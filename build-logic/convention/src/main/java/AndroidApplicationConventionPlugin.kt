import com.build_logic.configureKotlinAndroid
import com.build_logic.generateVersionCode
import com.build_logic.generateVersionName
import com.build_logic.libs
import com.android.build.api.dsl.ApplicationExtension
import com.build_logic.configureBuildFields
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")
            pluginManager.apply("com.google.gms.google-services")
            pluginManager.apply("com.google.firebase.crashlytics")

            extensions.configure<ApplicationExtension> {
                buildFeatures {
                    buildConfig = true
                    compose = true
                }

                configureKotlinAndroid(this)

                defaultConfig {
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    applicationId = libs.findVersion("applicationId").get().toString()
                    versionCode = project.generateVersionCode()
                    versionName = project.generateVersionName()
                }

                flavorDimensions += "version"
                productFlavors {
                    create("stage") { dimension = "version" }
                    create("product") { dimension = "version" }
                }

                configureBuildFields(this)

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }
        }
    }
}