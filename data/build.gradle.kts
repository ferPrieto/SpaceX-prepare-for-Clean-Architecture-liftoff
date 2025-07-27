plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(project(":domain"))
    testImplementation(project(":core-kotlin-test"))

    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)

    implementation(libs.timber)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.joda.time)
    testImplementation(libs.joda.time)

    testImplementation(libs.bundles.test.core)
}
