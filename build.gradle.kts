buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(prieto.fernando.dependencies.BuildDependencies.androidGradle)
        classpath(prieto.fernando.dependencies.BuildDependencies.kotlinGradlePlugin)
        classpath(prieto.fernando.dependencies.BuildDependencies.hiltAndroidGradlePlugin)
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
