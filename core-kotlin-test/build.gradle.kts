plugins {
    id("prieto.fernando.kotlin.plugin")
}

dependencies {
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.joda.time)
    
    implementation(libs.junit)
    implementation(libs.kotlin.test)
}
