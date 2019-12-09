import prieto.fernando.android.plugin.BuildType

plugins {
    id("com.android.application")
    id("prieto.fernando.android.plugin")
}

androidPlugin {
    buildType = BuildType.App
}

android {
    defaultConfig {
        applicationId = "prieto.fernando.spacex"
    }
    buildTypes {
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
    implementation(project(ProjectModules.repository))

    implementation(Dependencies.AndroidX.fragmentKtx)

    implementation(Dependencies.AndroidX.constraintlayout)
    implementation(Dependencies.AndroidX.legacySupport)
    implementation(Dependencies.AndroidX.lifecycleLivedataKtx)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)
}
