plugins {
    id("prieto.fernando.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "prieto.fernando.core.network"
    
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    testImplementation(project(":shared-testing"))

    api(libs.bundles.network)

    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(libs.joda.time)
    testImplementation(libs.joda.time)

    testImplementation(libs.bundles.test.core)
} 
