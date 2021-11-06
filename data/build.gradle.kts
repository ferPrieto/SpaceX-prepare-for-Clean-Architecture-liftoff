plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.jodaTime)
    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltAndroidCompiler)
    testImplementation(Dependencies.jodaTime)
}