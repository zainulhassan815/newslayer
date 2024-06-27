import java.util.Properties

plugins {
    alias(libs.plugins.newslayer.android.library)
    alias(libs.plugins.room)
    kotlin("plugin.serialization")
}

android {
    namespace = "org.dreamerslab.newslayer.core.data"

    defaultConfig {

        val localProperties = rootProject.file("local.properties").inputStream().use {
            Properties().apply { load(it) }
        }

        buildConfigField(
            type = "String",
            name = "NEWS_DATA_API",
            value = localProperties["NEWS_DATA_API"]?.toString() ?: "\"\""
        )
    }

    buildFeatures {
        buildConfig = true
    }

    room {
        schemaDirectory("${rootProject.projectDir}/schemas")
    }
}

dependencies {
    api(projects.core.model)

    implementation(projects.core.datastore)

    api(libs.arrow.core)
    implementation(libs.arrow.core.retrofit)

    implementation(libs.room.ktx)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)

    testImplementation(libs.junit)
}
