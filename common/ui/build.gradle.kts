plugins {
    alias(libs.plugins.newslayer.android.library)
    alias(libs.plugins.newslayer.android.library.compose)
}

android {
    namespace = "org.dreamerslab.newslayer.ui"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(projects.common.resources)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.material3)

    implementation(libs.coil.compose)
}
