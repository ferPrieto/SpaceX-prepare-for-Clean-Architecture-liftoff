package prieto.fernando.data_api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LaunchesResponse(
    @SerialName("mission_name")
    val missionName: String,
    @SerialName("launch_date_utc")
    val launchDate: String,
    val rocket: RocketResponse,
    val links: LinksResponse,
    @SerialName("launch_success")
    val launchSuccess: Boolean
)

@Serializable
data class RocketResponse(
    @SerialName("rocket_name")
    val rocketName: String,
    @SerialName("rocket_type")
    val rocketType: String
)

@Serializable
data class LinksResponse(
    @SerialName("mission_patch_small")
    val missionPatchSmall: String?,
    val wikipedia: String?,
    @SerialName("video_link")
    val videoLink: String?
)
