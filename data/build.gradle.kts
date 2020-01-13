plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.jodaTime)
    implementation(Dependencies.Dagger.daggerAndroid)

    testImplementation(Dependencies.jodaTime)
}