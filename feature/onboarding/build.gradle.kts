plugins {
    alias(libs.plugins.newslayer.android.feature)
    alias(libs.plugins.newslayer.android.library.compose)
}

android {
    namespace = "org.dreamerslab.newslayer.feature.onboarding"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

}

dependencies {
    implementation(projects.common.ui)
    implementation(projects.core.data)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.accompanist.permissions)
}
