plugins {
    alias(libs.plugins.newslayer.android.application)
    alias(libs.plugins.newslayer.android.application.compose)
    kotlin("plugin.serialization")
}

android {
    namespace = "org.dreamerslab.newslayer"

    defaultConfig {
        applicationId = "org.dreamerslab.newslayer"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.common.ui)

    implementation(projects.core.data)

    implementation(projects.feature.onboarding)
    implementation(projects.feature.home)
    implementation(projects.feature.search)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
