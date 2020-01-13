plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(Dependencies.Dagger.daggerAndroid)
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}