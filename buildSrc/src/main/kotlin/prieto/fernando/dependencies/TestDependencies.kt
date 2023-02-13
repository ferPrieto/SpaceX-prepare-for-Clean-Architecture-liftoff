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
            "androidx.arch.core:core-testing:${Versions.coreTesting}"


        const val composeUiTest =
            "androidx.compose.ui:ui-test:${Versions.compose}"
        const val composeUiTestJUnit4 =
                "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
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

    object Mockk {
        const val mockk ="io.mockk:mockk:${Versions.mockk}"
        const val mockkAgentJvm = "io.mockk:mockk-agent-jvm:${Versions.mockk}"
    }

    object Hilt{
        const val androidTesting =
            "com.google.dagger:hilt-android-testing:${Versions.hiltTest}"
        const val androidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltTest}"
    }

    const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
}
