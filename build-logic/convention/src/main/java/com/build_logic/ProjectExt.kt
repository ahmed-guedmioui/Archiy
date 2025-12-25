package com.build_logic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")


fun Project.generateVersionCode(): Int {
    val major = libs.findVersion("versionMajor").get().toString().toInt()
    val minor = libs.findVersion("versionMinor").get().toString().toInt()
    val patch = libs.findVersion("versionPatch").get().toString().toInt()
    return major * 10000 + minor * 100 + patch
}

fun Project.generateVersionName(): String {
    val major = libs.findVersion("versionMajor").get().toString().toInt()
    val minor = libs.findVersion("versionMinor").get().toString().toInt()
    val patch = libs.findVersion("versionPatch").get().toString().toInt()

    return "$major.$minor.$patch"
}