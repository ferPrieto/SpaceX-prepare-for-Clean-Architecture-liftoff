package prieto.fernando.dependencies

object TestDependencies {
    object AndroidX {
        const val core =
            "androidx.test:core:${Versions.androidxTest}"
        const val coreKtx =
            "androidx.test:core-ktx:${Versions.androidxTest}"
        const val runner =
            "androidx.test:runner:${Versions.androidxTest}"
        const val rules =
            "androidx.test:rules:${Versions.androidxTest}"
        const val espressoCore =
            "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib =
            "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val junit =
            "androidx.test.ext:junit:${Versions.androidxJunit}"
        const val coreTesting =
            "android.arch.core:core-testing:${Versions.coreTesting}"
    }

    const val kotlinxCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutines}"

    object JUnit {
        const val junit =
            "junit:junit:${Versions.junit}"
        const val junitPlatformRunner =
            "org.junit.platform:junit-platform-runner:${Versions.junitPlatformRunner}"
    }

    const val livedataTesting =
        "com.jraska.livedata:testing-ktx:${Versions.livedataTesting}"

    object Mockito {
        const val mockitoCore =
            "org.mockito:mockito-core:${Versions.mockito}"
        const val mockitoInline =
            "org.mockito:mockito-inline:${Versions.mockito}"
        const val mockitoKotlin =
            "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    }

    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
}
