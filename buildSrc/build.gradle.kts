repositories {
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:8.7.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")

    implementation(gradleApi())
}

