package prieto.fernando.dependencies

object Dependencies {
    object AndroidX {
        const val fragmentKtx =
            "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val coreKtx =
            "androidx.core:core-ktx:${Versions.core}"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val legacySupport =
            "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"
        const val lifecycleLivedataKtx =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleLivedataKtx}"
        const val lifecycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleLivedataKtx}"
        const val archComponents =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleLivedataKtx}"
        const val browser = "androidx.browser:browser:${Versions.browser}"

        object Navigation {
            const val fragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val uiKtx =
                "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val systemUiController =
                "com.google.accompanist:accompanist-systemuicontroller:${Versions.composeSystemUi}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
            const val runtimeLiveData =
                "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val uiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
            const val navigation =
                "androidx.navigation:navigation-compose:${Versions.composeNavigation}"
            const val viewModel =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModel}"
        }
    }

    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"

    object Dagger {
        const val dagger =
            "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerAndroid =
            "com.google.dagger:dagger-android:${Versions.dagger}"
        const val daggerAndroidSupport =
            "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val daggerCompiler =
            "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val daggerAndroidProcessor =
            "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object Hilt {
        const val hiltAndroid =
            "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltAndroidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val hiltCompiler =
            "androidx.hilt:hilt-compiler:${Versions.hiltJetpack}"
        const val hiltNavigationCompose =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltVM}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitConverterGson =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitConverterGson}"
    }

    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.lottieCompose}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val okHttpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingInterceptor}"

    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"

    const val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"
    const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
}
