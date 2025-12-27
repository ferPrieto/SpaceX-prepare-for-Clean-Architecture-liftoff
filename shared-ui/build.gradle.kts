plugins {
    id("ferprieto.android.plugin")
    alias(libs.plugins.kotlin.compose.compiler)
}

android {
    namespace = "ferprieto.shared.ui"
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
    
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Compose BOM and UI libraries
    implementation(libs.bundles.compose)
    
    // Animation and graphics
    implementation(libs.lottie.compose)
    
    // Utilities
    implementation(libs.timber)
} 