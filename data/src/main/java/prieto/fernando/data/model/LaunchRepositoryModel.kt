package prieto.fernando.data.model

import org.joda.time.DateTime

data class LaunchRepositoryModel(
    val missionName: String,
    val launchDateLocal: DateTime,
    val rocket: RocketRepositoryModel,
    val links: LinksRepositoryModel,
    val launchSuccess: Boolean
)

data class RocketRepositoryModel(
    val rocketName: String,
    val rocketType: String
)

data class LinksRepositoryModel(
    val missionPatchSmall: String,
    val wikipedia: String,
    val videoLink: String
)
