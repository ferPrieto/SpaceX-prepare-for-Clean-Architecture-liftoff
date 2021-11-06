plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("prieto.fernando.android.plugin")
    id("dagger.hilt.android.plugin")
}

androidPlugin{
    buildType = prieto.fernando.android.plugin.BuildType.App
}

android {
    defaultConfig {
        applicationId = "prieto.fernando.spacex"
        minSdk = AndroidSettings.minSdk
        targetSdk = AndroidSettings.targetSdk
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            buildConfigField("Integer", "PORT", "8080")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
    }
}

dependencies {
    implementation(project(ProjectModules.core))
    implementation(project(ProjectModules.navigation))
    implementation(project(ProjectModules.api))
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.data))

    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    implementation(Dependencies.AndroidX.Compose.viewModel)
    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    implementation(Dependencies.AndroidX.archComponents)
    implementation(Dependencies.AndroidX.browser)
    implementation(Dependencies.solidRecyclerView)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.uiTooling)
    implementation(Dependencies.AndroidX.Compose.runtime)
    implementation(Dependencies.AndroidX.Compose.runtimeLiveData)
    implementation(Dependencies.AndroidX.Compose.navigation)

    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltAndroidCompiler)
    implementation(Dependencies.Hilt.hiltViewModel)
    implementation(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltNavigationCompose)

    implementation(Dependencies.picasso)
    implementation(Dependencies.bottomSheet)
    implementation(Dependencies.lottie)

    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.jodaTime)

    testImplementation(Dependencies.jodaTime)
    testImplementation(project(ProjectModules.coreAndroidTest))
    testImplementation(project(ProjectModules.domain))

    androidTestImplementation(project(ProjectModules.coreAndroidTest))
    androidTestImplementation(TestDependencies.AndroidX.runner)
    androidTestImplementation(TestDependencies.AndroidX.rules)
    androidTestImplementation(TestDependencies.mockWebServer)
    androidTestImplementation(TestDependencies.AndroidX.core)
    androidTestImplementation(TestDependencies.AndroidX.coreKtx)
}
