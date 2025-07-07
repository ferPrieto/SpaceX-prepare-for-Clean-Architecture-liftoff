import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules

plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    testImplementation(project(ProjectModules.coreKotlinTest))

    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.daggerCompiler)
    implementation(Dependencies.Hilt.hilt)
    kapt(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}
