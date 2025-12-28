plugins {
    id("ferprieto.android.plugin")
    alias(libs.plugins.hilt)
}

android {
    namespace = "ferprieto.shared.testing.android"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {
    // Core Android Testing
    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.androidx.test.espresso.core)
    
    // Compose Testing
    implementation(libs.bundles.compose)
    implementation(libs.androidx.compose.ui.test.junit4)
    implementation(libs.androidx.compose.ui.test.manifest)
    
    // Hilt (for test modules using Hilt)
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
    kapt(libs.hilt.android.compiler)
    
    // MockWebServer
    implementation(libs.mockwebserver)
    
    // Coroutines Test
    implementation(libs.kotlinx.coroutines.test)
    
    // Timber
    implementation(libs.timber)
}

