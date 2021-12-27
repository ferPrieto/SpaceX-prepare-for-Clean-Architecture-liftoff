import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.ProjectModules
import prieto.fernando.dependencies.ProjectModules.api

plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.data))
    testImplementation(project(ProjectModules.coreAndroidTest))

    api(Dependencies.Retrofit.retrofit)
    api(Dependencies.Retrofit.retrofitConverterGson)
    api(Dependencies.okHttpLoggingInterceptor)

    implementation(Dependencies.Hilt.hiltAndroid)
    annotationProcessor(Dependencies.Hilt.hiltAndroidCompiler)

    implementation(Dependencies.jodaTime)

    testImplementation(Dependencies.jodaTime)
}