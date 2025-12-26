plugins {
    id("prieto.fernando.android.plugin")
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "prieto.fernando.feature.dashboard"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 26
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    
    buildFeatures {
        compose = true
        buildConfig = true
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core modules
    implementation(project(":core-network"))  // For API service and response models
    
    // Shared modules
    implementation(project(":shared-ui"))
    
    // Compose
    implementation(libs.bundles.compose)
    
    // Animation
    implementation(libs.lottie.compose)
    
    // Dependency Injection
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    
    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    
    // Utilities
    implementation(libs.timber)
    implementation(libs.joda.time)
    
    // Testing
    testImplementation(project(":shared-testing"))
    testImplementation(libs.bundles.test.core)
    testImplementation(libs.androidx.arch.core.testing)
    testImplementation(libs.mockk)
    
    // Android Testing
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test.manifest) // For createComposeRule
    androidTestImplementation(project(":shared-ui"))
}

// Workaround for Hilt 2.48+ / JavaPoet compatibility issue
hilt {
    enableAggregatingTask = false
}

