package org.dreamerslab.newslayer

import org.gradle.api.Project
import com.diffplug.gradle.spotless.SpotlessExtension

internal fun Project.configureSpotless(
    extension: SpotlessExtension
) {
    extension.apply {
        val ktlintVersion = libs.findVersion("ktlint").get().requiredVersion

        kotlin {
            target("src/**/*.kt")
            ktlint(ktlintVersion)
                .customRuleSets(listOf("io.nlopez.compose.rules:ktlint:0.4.4"))
        }

        kotlinGradle {
            target("*.kts")
            ktlint(ktlintVersion)
        }
    }
}
