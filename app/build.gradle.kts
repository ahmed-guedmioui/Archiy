import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern

plugins {
    alias(libs.plugins.convention.android.application)
}

android {
    namespace = "com.app"

    applicationVariants.all {
        outputs.all {
            val output = this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
            outputFileName = "archiy_${getCurrentFlavor()}_${versionName}.apk"
            output.outputFileName = "archiy_${flavorName}_${versionName}.apk"
        }
    }
}

dependencies {
    // Core data
    implementation(projects.core.data)

    // Core domain
    implementation(projects.core.domain)

    // Core presentation
    implementation(projects.core.presentation)

    // Navigation root
    implementation(projects.navRoot.impl)

    // Splash screen
    implementation(libs.splashscreen)
}

fun getCurrentFlavor(): String {
    val gradle = getGradle()
    val tskReqStr = gradle.startParameter.taskRequests.toString()

    val pattern: Pattern = when {
        tskReqStr.contains("assemble") -> Pattern.compile("assemble(\\w+)(Release|Debug)")
        tskReqStr.contains("bundle") -> Pattern.compile("assemble(\\w+)(Release|Debug)")
        else -> Pattern.compile("generate(\\w+)(Release|Debug)")
    }

    val matcher: Matcher = pattern.matcher(tskReqStr)

    return if (matcher.find()) {
        matcher.group(1)?.lowercase(Locale.getDefault()) ?: ""
    } else {
        println("NO MATCH FOUND")
        ""
    }
}