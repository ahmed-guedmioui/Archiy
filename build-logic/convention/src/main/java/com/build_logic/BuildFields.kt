package com.build_logic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import java.util.Locale

internal fun Project.configureBuildFields(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        buildTypes.forEach {
            val flavor = getCurrentFlavor(this@configureBuildFields)

            val baseUrl = when (flavor) {
                "stage" -> project.property("stageBaseUrl").toString()
                else -> project.property("prodBaseUrl").toString()
            }
            it.buildConfigField("String", "BASE_URL", baseUrl)
        }
    }
}

private fun getCurrentFlavor(project: Project): String {
    val tsk = project.gradle.startParameter.taskRequests.toString()

    val regex = when {
        tsk.contains("assemble") -> "assemble(\\w+)(Release|Debug)"
        tsk.contains("bundle") -> "bundle(\\w+)(Release|Debug)"
        else -> "generate(\\w+)(Release|Debug)"
    }.toRegex()

    val match = regex.find(tsk)
    return match?.groups?.get(1)?.value?.lowercase(Locale.getDefault()) ?: ""
}