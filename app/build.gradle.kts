plugins {
    id("prieto.fernando.android.plugin")
    alias(libs.plugins.hilt)
    id("shot")
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "prieto.fernando.spacex"
    defaultConfig {
        applicationId = "prieto.fernando.spacex"
        testInstrumentationRunner = "prieto.fernando.spacex.webmock.MockAndShotTestRunner"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
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

kapt {
    includeCompileClasspath = false
}

dependencies {
    implementation(project(":data-api"))
    implementation(project(":shared-ui"))
    implementation(project(":feature-dashboard"))
    implementation(project(":feature-launches"))

    implementation(libs.bundles.androidx.core)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.navigation)
    kapt(libs.androidx.lifecycle.compiler)

    implementation(libs.bundles.compose)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.hilt.compiler)

    implementation(libs.bundles.ui.libraries)

    implementation(libs.timber)
    implementation(libs.joda.time)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.joda.time)
    testImplementation(project(":shared-testing"))
    testImplementation(project(":domain"))

    androidTestImplementation(project(":shared-testing"))
    androidTestImplementation(libs.bundles.test.androidx)
    androidTestImplementation(libs.bundles.test.compose)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    androidTestImplementation(libs.mockwebserver)

    androidTestImplementation(libs.hilt.android.testing)
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
        }
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}

tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
