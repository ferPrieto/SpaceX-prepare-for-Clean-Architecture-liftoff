import prieto.fernando.dependencies.Dependencies
import prieto.fernando.dependencies.TestDependencies

plugins {
    id("com.android.library")
    id("prieto.fernando.android.plugin")
}

dependencies {
    implementation(TestDependencies.kotlinxCoroutines)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(TestDependencies.AndroidX.espressoCore)
    implementation(TestDependencies.AndroidX.espressoContrib)

    implementation(Dependencies.jodaTime)

}