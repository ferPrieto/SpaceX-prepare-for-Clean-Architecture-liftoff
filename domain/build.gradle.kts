plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}