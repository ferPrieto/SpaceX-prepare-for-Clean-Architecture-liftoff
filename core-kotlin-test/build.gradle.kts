import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.TestDependencies

plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(TestDependencies.kotlinxCoroutines)
    implementation(Dependencies.jodaTime)
}