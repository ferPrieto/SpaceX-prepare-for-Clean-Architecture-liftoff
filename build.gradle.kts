buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
        classpath(prieto.fernando.dependencies.BuildDependencies.karumiShotGradlePlugin)
        classpath("com.facebook.testing.screenshot:plugin:0.14.0")
    }
}

allprojects {

    repositories {
        google()
        jcenter()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}