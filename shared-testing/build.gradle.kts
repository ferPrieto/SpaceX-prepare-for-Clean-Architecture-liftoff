plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    // Coroutines testing
    implementation(libs.kotlinx.coroutines.test)
    
    // Date/Time
    implementation(libs.joda.time)
    
    // Core testing libraries
    implementation(libs.junit)
    implementation(libs.kotlin.test)
    
    // Mocking
    implementation(libs.mockk)
}

