plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(project(":data"))
    testImplementation(project(":core-kotlin-test"))

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
