buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildDependencies.androidGradle)
        classpath(BuildDependencies.kotlinGradlePlugin)
        classpath (BuildDependencies.hiltAndroidGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://dl.bintray.com/shadowcra/Solid")
    }
}

task("clean") {
    delete(rootProject.buildDir)
}
