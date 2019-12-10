package prieto.fernando.data_repository.model

import java.util.*

data class LaunchRepositoryModel(
    val missionName: String,
    val launchDateLocal: Date,
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
