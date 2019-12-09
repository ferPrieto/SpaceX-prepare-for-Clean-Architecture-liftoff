plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.presentation))

    implementation(Dependencies.Rx.rxAndroid)
    implementation(Dependencies.Rx.rxJava)
    implementation(Dependencies.Rx.rxKotlin)
    implementation(Dependencies.Rx.rxBinding)

    implementation(Dependencies.Dagger.daggerAndroid)
}