buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(prieto.fernando.dependencies.BuildDependencies.androidGradle)
        classpath(prieto.fernando.dependencies.BuildDependencies.kotlinGradlePlugin)
        classpath(prieto.fernando.dependencies.BuildDependencies.hiltAndroidGradlePlugin)
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
