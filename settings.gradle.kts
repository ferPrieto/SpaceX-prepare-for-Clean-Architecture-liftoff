dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

rootProject.name = "SpaceX"
include(":app")
include(":core-kotlin-test")
include(":data")
include(":data-api")
include(":domain")
include(":shared-ui")
