repositories {
    jcenter()
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")

    implementation(gradleApi())
}

