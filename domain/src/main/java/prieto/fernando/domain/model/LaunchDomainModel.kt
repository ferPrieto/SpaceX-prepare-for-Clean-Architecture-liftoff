package prieto.fernando.domain.model

import java.util.*

data class LaunchDomainModel(
    val missionName: String,
    val launchDate: Date,
    val rocket: RocketDomainModel,
    val links: LinksDomainModel,
    val launchSuccess: Boolean
)

data class RocketDomainModel(
    val rocketName: String,
    val rocketType: String
)

data class LinksDomainModel(
    val missionPatchSmall: String,
    val wikipedia: String,
    val videoLink: String
)
