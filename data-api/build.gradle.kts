plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(project(ProjectModules.repository))

    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.retrofitConverterGson)
    implementation(Dependencies.Retrofit.retrofitRxjava2Adapter)
    implementation(Dependencies.okHttpLoggingInterceptor)

    implementation(Dependencies.Dagger.daggerAndroid)
}