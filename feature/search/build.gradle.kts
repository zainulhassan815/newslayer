plugins {
    alias(libs.plugins.newslayer.android.feature)
    alias(libs.plugins.newslayer.android.library.compose)
}

android {
    namespace = "org.dreamerslab.newslayer.feature.search"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(projects.common.ui)
    implementation(projects.core.data)

    implementation(libs.androidx.paging.compose)
}
