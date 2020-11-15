plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    api(Dependencies.Dagger.daggerAndroidSupport)

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    annotationProcessor(Dependencies.AndroidX.archViewModel)
    implementation(Dependencies.AndroidX.archComponents)
}