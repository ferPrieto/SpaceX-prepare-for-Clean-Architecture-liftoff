package prieto.fernando.domain.model

import org.joda.time.DateTime

data class LaunchDomainModel(
    val missionName: String,
    val launchDate: DateTime,
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
