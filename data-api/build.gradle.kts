import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules

plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.data))
    testImplementation(project(ProjectModules.coreKotlinTest))

    api(Dependencies.Retrofit.retrofit)
    api(Dependencies.Retrofit.retrofitConverterGson)
    api(Dependencies.okHttpLoggingInterceptor)

    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.hiltAndroidCompiler)

    implementation(Dependencies.kotlinxSerialization)

    implementation(Dependencies.jodaTime)
    testImplementation(Dependencies.jodaTime)
}

android {
    namespace = "prieto.fernando.spacex.data.api"
    buildFeatures {
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
} 
