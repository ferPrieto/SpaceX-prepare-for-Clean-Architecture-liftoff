import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules

plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    testImplementation(project(ProjectModules.coreKotlinTest))

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}
