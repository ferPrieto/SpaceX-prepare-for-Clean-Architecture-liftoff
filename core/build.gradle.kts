plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    api(Dependencies.Dagger.dagger)
    api(Dependencies.Dagger.daggerAndroid)
    api(Dependencies.Dagger.daggerAndroidSupport)

    api(Dependencies.Rx.rxAndroid)
    api(Dependencies.Rx.rxJava)
    api(Dependencies.Rx.rxKotlin)
    api(Dependencies.Rx.rxBinding)

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
}