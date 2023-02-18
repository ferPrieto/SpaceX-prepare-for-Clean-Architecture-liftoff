import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules
import prieto.fernando.dependencies.TestDependencies

plugins {
    id("com.android.application")
    kotlin("android")
    id("prieto.fernando.android.plugin")
    id("dagger.hilt.android.plugin")
    id("shot")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

androidPlugin {
    buildType = prieto.fernando.android.plugin.BuildType.App
}

android {
    defaultConfig {
        applicationId = "prieto.fernando.spacex"
        minSdk = prieto.fernando.dependencies.AndroidSettings.minSdk
        targetSdk = prieto.fernando.dependencies.AndroidSettings.targetSdk
        testInstrumentationRunner = "prieto.fernando.spacex.webmock.MockAndShotTestRunner"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            // AGP 7.3+
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
            // AGP before 7.3
            isTestCoverageEnabled = true
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
    implementation(project(ProjectModules.api))
    implementation(project(ProjectModules.domain))
    implementation(project(ProjectModules.data))

    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    implementation(Dependencies.AndroidX.Compose.viewModel)
    kapt(Dependencies.AndroidX.lifecycleCompiler)
    implementation(Dependencies.AndroidX.archComponents)
    implementation(Dependencies.AndroidX.browser)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.systemUiController)
    implementation(Dependencies.AndroidX.Compose.material)
    implementation(Dependencies.AndroidX.Compose.uiTooling)
    implementation(Dependencies.AndroidX.Compose.runtime)
    implementation(Dependencies.AndroidX.Compose.runtimeLiveData)
    implementation(Dependencies.AndroidX.Compose.navigation)

    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltAndroidCompiler)
    implementation(Dependencies.Hilt.hiltCompiler)
    implementation(Dependencies.Hilt.hiltNavigationCompose)

    implementation(Dependencies.coilCompose)
    implementation(Dependencies.lottie)
    implementation(Dependencies.lottieCompose)

    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
    implementation(Dependencies.jodaTime)

    testImplementation(Dependencies.jodaTime)
    testImplementation(project(ProjectModules.coreAndroidTest))
    testImplementation(project(ProjectModules.domain))

    androidTestImplementation(project(ProjectModules.coreAndroidTest))
    androidTestImplementation(TestDependencies.AndroidX.core)
    androidTestImplementation(TestDependencies.AndroidX.coreKtx)
    androidTestImplementation(TestDependencies.AndroidX.runner)
    androidTestImplementation(TestDependencies.AndroidX.rules)
    androidTestImplementation(TestDependencies.AndroidX.composeUiTest)
    androidTestImplementation(TestDependencies.AndroidX.composeUiTestJUnit4)
    debugImplementation(TestDependencies.AndroidX.uiTestManifest)

    androidTestImplementation(TestDependencies.mockWebServer)

    androidTestImplementation(TestDependencies.Hilt.androidTesting)
    kaptAndroidTest(TestDependencies.Hilt.androidCompiler)
    androidTestAnnotationProcessor(TestDependencies.Hilt.androidCompiler)
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(true) // simple Markdown format
    }
}

tasks.named("check").configure {
    this.setDependsOn(
        this.dependsOn.filterNot {
            it is TaskProvider<*> && it.name == "detekt"
        }
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
