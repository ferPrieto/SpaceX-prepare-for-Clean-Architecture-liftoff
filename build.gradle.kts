buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildDependencies.androidGradle)
        classpath(BuildDependencies.kotlinGradlePlugin)
        classpath(BuildDependencies.hiltAndroidGradlePlugin)
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
