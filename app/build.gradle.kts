import prieto.fernando.dependencies.ProjectModules
import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.TestDependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("prieto.fernando.android.plugin")
}

androidPlugin{
    buildType = prieto.fernando.android.plugin.BuildType.App
}

android {
    defaultConfig {
        applicationId = "prieto.fernando.spacex"
        minSdk = prieto.fernando.dependencies.AndroidSettings.minSdk
        targetSdk = prieto.fernando.dependencies.AndroidSettings.targetSdk
        testInstrumentationRunner = "prieto.fernando.spacex.webmock.MockTestRunner"
    }

    buildFeatures {
        viewBinding = true
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
    implementation(project(ProjectModules.presentation))
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.data))
    androidTestImplementation(project(ProjectModules.coreAndroidTest))
    testImplementation(project(ProjectModules.coreAndroidTest))

    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    annotationProcessor(Dependencies.AndroidX.lifecycleCompiler)
    implementation(Dependencies.AndroidX.archComponents)
    implementation(Dependencies.AndroidX.browser)
    implementation(Dependencies.solidRecyclerView)

    implementation(Dependencies.coil)
    implementation(Dependencies.bottomSheet)
    implementation(Dependencies.lottie)

    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)

    androidTestImplementation(TestDependencies.AndroidX.runner)
    androidTestImplementation(TestDependencies.AndroidX.rules)
    androidTestImplementation(TestDependencies.mockWebServer)
    androidTestImplementation(TestDependencies.AndroidX.core)
    androidTestImplementation(TestDependencies.AndroidX.coreKtx)
}
