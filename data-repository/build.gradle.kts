plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.domain))

    implementation(Dependencies.Rx.rxAndroid)
    implementation(Dependencies.Rx.rxJava)
    implementation(Dependencies.Rx.rxKotlin)
    implementation(Dependencies.Rx.rxBinding)

    implementation(Dependencies.jodaTime)
    implementation(Dependencies.Dagger.daggerAndroid)

    testImplementation(Dependencies.jodaTime)
}