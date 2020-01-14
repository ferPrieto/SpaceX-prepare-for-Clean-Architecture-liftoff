rootProject.name = "SpaceX"
rootProject.buildFileName = "build.gradle.kts"

include(
    ":app",
    ProjectModules.core,
    ProjectModules.navigation,
    ProjectModules.api,
    ProjectModules.data,
    ProjectModules.domain,
    ProjectModules.presentation,
    ProjectModules.coreAndroidTest
)
