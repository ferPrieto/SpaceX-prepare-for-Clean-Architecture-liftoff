package prieto.fernando.spacex.presentation.launches

data class Launch(
    val missionName: String,
    val launchDate: String,
    val isPastLaunch: Boolean,
    val differenceOfDays: String,
    val rocket: Rocket,
    val links: Links,
    val launchSuccess: Boolean
)

data class Rocket(
    val rocketName: String,
    val rocketType: String
)

data class Links(
    val missionPatchSmall: String,
    val wikipedia: String,
    val videoLink: String
)
