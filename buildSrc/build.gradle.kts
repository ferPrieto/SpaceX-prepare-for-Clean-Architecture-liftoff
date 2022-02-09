repositories {
    jcenter()
    google()
    mavenCentral()
}

plugins {
    `kotlin-dsl`
    jacoco
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.android.tools.build:gradle:7.1.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")

    implementation(gradleApi())
    implementation(kotlin("script-runtime"))
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }

    finalizedBy("jacocoTestCoverageVerification")
}
