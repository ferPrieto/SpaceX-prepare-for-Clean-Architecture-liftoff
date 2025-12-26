plugins {
    id("prieto.fernando.kotlin.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
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
