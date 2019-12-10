package prieto.fernando.presentation.model

data class LaunchUiModel(
    val missionName: String,
    val launchDate: String,
    val isPastLaunch: Boolean,
    val differenceOfDays: String,
    val rocket: RocketUiModel,
    val links: LinksUiModel,
    val launchSuccess: Boolean
)

data class RocketUiModel(
    val rocketName: String,
    val rocketType: String
)

data class LinksUiModel(
    val missionPatchSmall: String,
    val wikipedia: String,
    val videoLink: String
)
