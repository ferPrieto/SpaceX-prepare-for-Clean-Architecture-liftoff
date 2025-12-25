plugins {
    id("prieto.fernando.android.plugin")
    alias(libs.plugins.hilt)
}

android {
    namespace = "prieto.fernando.feature.launches"
    compileSdk = 35
    
    defaultConfig {
        minSdk = 26
        
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
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core modules
    implementation(project(":domain"))
    
    // Shared modules
    implementation(project(":shared-ui"))
    
    // Compose
    implementation(libs.bundles.compose)
    
    // Animation
    implementation(libs.lottie.compose)
    
    // Image loading
    implementation(libs.coil.compose)
    
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
}

