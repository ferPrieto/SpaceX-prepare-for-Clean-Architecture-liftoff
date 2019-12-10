package prieto.fernando.domain.model

data class LaunchDomainModel(
    val missionName: String,
    val launchDateLocal: String,
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
