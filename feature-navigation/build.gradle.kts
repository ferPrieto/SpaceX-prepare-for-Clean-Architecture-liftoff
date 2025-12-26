plugins {
    id("prieto.fernando.android.plugin")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.compose.compiler)
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "prieto.fernando.feature.navigation"

    buildFeatures {
        compose = true
        buildConfig = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Feature modules
    implementation(project(":feature-dashboard"))
    implementation(project(":feature-launches"))
    
    // Shared modules
    implementation(project(":shared-ui"))

    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.bundles.androidx.navigation)
    
    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    
    // AndroidX Core
    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.androidx.lifecycle)
    
    // Utilities
    implementation(libs.timber)
    implementation(libs.kotlinx.serialization.json)
}

