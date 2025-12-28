plugins {
    id("ferprieto.android.plugin")
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose.compiler)
    id("shot")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "ferprieto.spacex"
    defaultConfig {
        applicationId = "ferprieto.spacex"
        testInstrumentationRunner = "ferprieto.shared.testing.android.webmock.MockAndShotTestRunner"
        buildConfigField("int", "PORT", "8080")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
        freeCompilerArgs += "-Xsuppress-version-warnings"
    }
}

dependencies {
    // Core modules
    implementation(project(":core-network"))

    // Feature modules - explicit dependencies for Hilt aggregation
    implementation(project(":feature-dashboard"))
    implementation(project(":feature-launches"))
    implementation(project(":feature-navigation"))
    implementation(project(":shared-ui"))

    // Compose (for setContent and theme)
    implementation(libs.bundles.compose)

    // Hilt (for @HiltAndroidApp and @AndroidEntryPoint)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)

    // Kotlinx Serialization (needed for core-network's Hilt module)
    implementation(libs.kotlinx.serialization.json)

    // Activity Compose (for ComponentActivity and setContent)
    implementation(libs.bundles.androidx.core)

    // Testing
    testImplementation(project(":shared-testing"))
    androidTestImplementation(project(":shared-testing-android"))
    androidTestImplementation(libs.bundles.test.androidx)
    androidTestImplementation(libs.bundles.test.compose)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.mockwebserver)
    kaptAndroidTest(libs.hilt.android.compiler)
    androidTestAnnotationProcessor(libs.hilt.android.compiler)
}

// Workaround for Hilt 2.48 + JavaPoet 1.13.0 compatibility issue
// Based on: https://github.com/google/dagger/issues/4048
hilt {
    enableAggregatingTask = false
}

detekt {
    buildUponDefaultConfig = true
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
        },
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
