buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(BuildDependencies.androidGradle)
        classpath(BuildDependencies.kotlinGradlePlugin)
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
