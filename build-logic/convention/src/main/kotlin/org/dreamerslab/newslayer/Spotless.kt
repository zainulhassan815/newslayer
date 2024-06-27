package org.dreamerslab.newslayer

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Project

internal fun Project.configureSpotless(extension: SpotlessExtension) {
    extension.apply {
        val ktlintVersion = libs.findVersion("ktlint").get().requiredVersion

        kotlin {
            target("src/**/*.kt")
            ktlint(ktlintVersion)
                .setEditorConfigPath("${rootProject.file("config/ktlint.editorconfig")}")
                .customRuleSets(listOf("io.nlopez.compose.rules:ktlint:0.4.4"))
        }

        kotlinGradle {
            target("*.kts")
            ktlint(ktlintVersion)
        }
    }
}
