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
    implementation("com.android.tools.build:gradle:3.5.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")

    implementation(gradleApi())
}
