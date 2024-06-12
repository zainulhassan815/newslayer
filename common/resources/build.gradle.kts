plugins {
    alias(libs.plugins.newslayer.android.library)
}

android {
    namespace = "org.dreamerslab.newslayer.common.resources"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}
