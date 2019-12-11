plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.repository))

    api(Dependencies.Retrofit.retrofit)
    api(Dependencies.Retrofit.retrofitConverterGson)
    api(Dependencies.Retrofit.retrofitRxjava2Adapter)
    api(Dependencies.okHttpLoggingInterceptor)

    implementation(Dependencies.Dagger.daggerAndroid)
    testImplementation(TestDependencies.AndroidX.coreTesting)
}