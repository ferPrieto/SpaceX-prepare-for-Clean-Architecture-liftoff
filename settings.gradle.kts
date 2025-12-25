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
include(":shared-testing")
include(":core-network")
include(":shared-ui")
include(":feature-dashboard")
include(":feature-launches")
