plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.core))
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)

    implementation(Dependencies.timber)
    implementation(Dependencies.jodaTime)
    implementation(Dependencies.Dagger.daggerAndroid)
    implementation(Dependencies.Retrofit.retrofit)

    testImplementation(Dependencies.jodaTime)
}